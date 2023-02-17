package com.backendless.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import com.backendless.BackendlessInjector;
import com.backendless.messaging.Action;
import com.backendless.messaging.AndroidPushTemplate;
import com.backendless.messaging.PublishOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PushTemplateHelper
{
  private static BackendlessInjector injector = BackendlessInjector.getInstance();

  static Bundle prepareMessageBundle( final Bundle rawMessageBundle, final AndroidPushTemplate template, final int notificationId )
  {
    Bundle newBundle = new Bundle();

    if( template.getCustomHeaders() != null && !template.getCustomHeaders().isEmpty() )
    {
      for( Map.Entry<String, String> header : template.getCustomHeaders().entrySet() )
        newBundle.putString( header.getKey(), header.getValue() );
    }

    newBundle.putAll( rawMessageBundle );

    String contentTitle = rawMessageBundle.getString( PublishOptions.ANDROID_CONTENT_TITLE_TAG );
    String summarySubText = rawMessageBundle.getString( PublishOptions.ANDROID_SUMMARY_SUBTEXT_TAG );

    contentTitle = contentTitle != null ? contentTitle : template.getContentTitle();
    summarySubText = summarySubText != null ? summarySubText : template.getSummarySubText();

    newBundle.putString( PublishOptions.ANDROID_CONTENT_TITLE_TAG, contentTitle );
    newBundle.putString( PublishOptions.ANDROID_SUMMARY_SUBTEXT_TAG, summarySubText );
    newBundle.putInt( PublishOptions.NOTIFICATION_ID, notificationId );
    newBundle.putString( PublishOptions.TEMPLATE_NAME, template.getName() );

    return newBundle;
  }

  static Notification convertFromTemplate( final Context context, final AndroidPushTemplate template, final Bundle newBundle,
                                           int notificationId )
  {
    Context appContext = context.getApplicationContext();
    // Notification channel ID is ignored for Android 7.1.1 (API level 25) and lower.

    String messageText = newBundle.getString( PublishOptions.MESSAGE_TAG );

    String contentTitle = newBundle.getString( PublishOptions.ANDROID_CONTENT_TITLE_TAG );
    contentTitle = contentTitle != null ? contentTitle : template.getContentTitle();

    String summarySubText = newBundle.getString( PublishOptions.ANDROID_SUMMARY_SUBTEXT_TAG );
    summarySubText = summarySubText != null ? summarySubText : template.getSummarySubText();

    String largeIcon = newBundle.getString( PublishOptions.ANDROID_LARGE_ICON_TAG );
    largeIcon = largeIcon != null ? largeIcon : template.getLargeIcon();

    String attachmentUrl = newBundle.getString( PublishOptions.ANDROID_ATTACHMENT_URL_TAG );
    attachmentUrl = attachmentUrl != null ? attachmentUrl : template.getAttachmentUrl();

    NotificationChannel notificationChannel = getOrCreateNotificationChannel( appContext, template );
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( appContext, notificationChannel.getId() );

    if( template.getBadge() != null && (template.getBadge() == NotificationCompat.BADGE_ICON_SMALL || template.getBadge() == NotificationCompat.BADGE_ICON_LARGE) )
      notificationBuilder.setBadgeIconType( template.getBadge() );
    else
      notificationBuilder.setBadgeIconType( NotificationCompat.BADGE_ICON_NONE );

    if( template.getBadgeNumber() != null )
      notificationBuilder.setNumber( template.getBadgeNumber() );

    if( template.getCancelAfter() != null && template.getCancelAfter() != 0 )
      notificationBuilder.setTimeoutAfter( template.getCancelAfter() * 1000 );

    if( attachmentUrl != null )
    {
      try
      {
        InputStream is = (InputStream) new URL( attachmentUrl ).getContent();
        Bitmap bitmap = BitmapFactory.decodeStream( is );

        if( bitmap != null )
          notificationBuilder.setStyle( new NotificationCompat.BigPictureStyle().bigPicture( bitmap ) );
        else
          Log.i( PushTemplateHelper.class.getSimpleName(), "Cannot convert rich media for notification into bitmap." );
      }
      catch( IOException e )
      {
        Log.e( PushTemplateHelper.class.getSimpleName(), "Cannot receive rich media for notification." );
      }
    }
    else if( messageText.length() > 35 )
    {
      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle().setBigContentTitle( contentTitle ).setSummaryText( summarySubText ).bigText( messageText );
      notificationBuilder.setStyle( bigText );
    }

    if( largeIcon != null )
    {
      if( largeIcon.startsWith( "http" ) )
      {
        try
        {
          InputStream is = (InputStream) new URL( largeIcon ).getContent();
          Bitmap bitmap = BitmapFactory.decodeStream( is );

          if( bitmap != null )
            notificationBuilder.setLargeIcon( bitmap );
          else
            Log.i( PushTemplateHelper.class.getSimpleName(), "Cannot convert Large Icon into bitmap." );
        }
        catch( IOException e )
        {
          Log.e( PushTemplateHelper.class.getSimpleName(), "Cannot receive bitmap for Large Icon." );
        }
      }
      else
      {
        int largeIconResource = appContext.getResources().getIdentifier( largeIcon, "drawable", appContext.getPackageName() );
        if( largeIconResource != 0 )
        {
          Bitmap bitmap = BitmapFactory.decodeResource( appContext.getResources(), largeIconResource );
          notificationBuilder.setLargeIcon( bitmap );
        }
      }
    }

    int icon = 0;

    // try to get icon from template
    if( template.getIcon() != null )
    {
      icon = appContext.getResources().getIdentifier( template.getIcon(), "mipmap", appContext.getPackageName() );

      if( icon == 0 )
        icon = appContext.getResources().getIdentifier( template.getIcon(), "drawable", appContext.getPackageName() );
    }

    // try to get default icon
    if( icon == 0 )
    {
      icon = context.getApplicationInfo().icon;
      if( icon == 0 )
        icon = android.R.drawable.sym_def_app_icon;
    }

    if( icon != 0 )
      notificationBuilder.setSmallIcon( icon );

    if( template.getLightsColor() != null && template.getLightsOnMs() != null && template.getLightsOffMs() != null )
      notificationBuilder.setLights( template.getLightsColor() | 0xFF000000, template.getLightsOnMs(), template.getLightsOffMs() );

    if( template.getColorCode() != null )
      notificationBuilder.setColor( template.getColorCode() | 0xFF000000 );

    if( template.getCancelOnTap() != null )
      notificationBuilder.setAutoCancel( template.getCancelOnTap() );
    else
      notificationBuilder.setAutoCancel( false );

    notificationBuilder.setShowWhen( true ).setWhen( System.currentTimeMillis() ).setContentTitle( contentTitle != null ? contentTitle : template.getContentTitle() ).setSubText( summarySubText != null ? summarySubText : template.getSummarySubText() ).setContentText( messageText );

    Intent notificationIntent;
    if( template.getActionOnTap() == null || template.getActionOnTap().isEmpty() )
      notificationIntent = appContext.getPackageManager().getLaunchIntentForPackage( appContext.getPackageName() );
    else
    {
      notificationIntent = new Intent( "ActionOnTap" );
      notificationIntent.setClassName( appContext, template.getActionOnTap() );
    }

    notificationIntent.putExtras( newBundle );
    notificationIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
    PendingIntent contentIntent = PendingIntent.getActivity( appContext, notificationId * 3, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT );

    // user should use messageId and tag(templateName) to cancel notification.
    notificationBuilder.setContentIntent( contentIntent );

    if( template.getActions() != null )
    {
      List<NotificationCompat.Action> actions = createActions( appContext, template.getActions(), newBundle, notificationId );
      for( NotificationCompat.Action action : actions )
        notificationBuilder.addAction( action );
    }

    return notificationBuilder.build();
  }

  static Uri getSoundUri( Context context, String resource )
  {
    Uri soundUri;
    if( resource != null && !resource.isEmpty() )
    {
      int soundResource = context.getResources().getIdentifier( resource, "raw", context.getPackageName() );
      soundUri = Uri.parse( "android.resource://" + context.getPackageName() + "/" + soundResource );
    }
    else
      soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

    return soundUri;
  }

  static private List<NotificationCompat.Action> createActions( final Context appContext, final Action[] actions, final Bundle bundle,
                                                                int notificationId )
  {
    List<NotificationCompat.Action> notifActions = new ArrayList<>();

    int i = 1;
    for( Action a : actions )
    {
      Intent actionIntent = new Intent( a.getTitle() );
      actionIntent.setClassName( appContext, a.getId() );
      actionIntent.putExtras( bundle );
      actionIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );

      // user should use messageId and tag(templateName) to cancel notification.

      PendingIntent pendingIntent = PendingIntent.getActivity( appContext, notificationId * 3 + i++, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT );

      NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder( 0, a.getTitle(), pendingIntent );

      if( a.getOptions() == 1 )
      {
        RemoteInput remoteInput = new RemoteInput.Builder( PublishOptions.INLINE_REPLY ).build();
        actionBuilder.setAllowGeneratedReplies( true ).addRemoteInput( remoteInput );
      }
      notifActions.add( actionBuilder.build() );
    }

    return notifActions;
  }

  static public void deleteNotificationChannel( Context context )
  {
    NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
    List<NotificationChannel> notificationChannels = notificationManager.getNotificationChannels();
    final String channelNotificationPrefix = getChannelNotificationPrefix();
    for( NotificationChannel notifChann : notificationChannels )
    {
      String notifChannId = notifChann.getId();
      // Delete NotificationChannel only if Backendless created this channel
      if( notifChannId.startsWith( channelNotificationPrefix ) )
      {
        notificationManager.deleteNotificationChannel( notifChannId );
      }
    }
  }

  static String getChannelId( String channelName )
  {
    return getChannelNotificationPrefix() + ":" + channelName;
  }

  static private String getChannelNotificationPrefix()
  {
    return injector.getPrefs().getApplicationIdOrDomain();
  }

  static public NotificationChannel getNotificationChannel( final Context context, final String templateName )
  {
    final String channelId = getChannelId( templateName );
    NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
    return notificationManager.getNotificationChannel( channelId );
  }

  static public NotificationChannel getOrCreateNotificationChannel( Context context, final AndroidPushTemplate template )
  {
    final String channelId = getChannelId( template.getName() );
    NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );

    NotificationChannel notificationChannel = notificationManager.getNotificationChannel( channelId );

    if( notificationChannel != null )
      return notificationChannel;

    notificationChannel = new NotificationChannel( channelId, template.getName(), NotificationManager.IMPORTANCE_DEFAULT );
    PushTemplateHelper.updateNotificationChannel( context, notificationChannel, template );
    notificationManager.createNotificationChannel( notificationChannel );
    return notificationChannel;
  }

  static private NotificationChannel updateNotificationChannel( Context context, NotificationChannel notificationChannel,
                                                                final AndroidPushTemplate template )
  {
    if( template.getShowBadge() != null )
      notificationChannel.setShowBadge( template.getShowBadge() );

    if( template.getPriority() != null && template.getPriority() > 0 && template.getPriority() < 6 )
      notificationChannel.setImportance( template.getPriority() ); // NotificationManager.IMPORTANCE_DEFAULT

    AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage( AudioAttributes.USAGE_NOTIFICATION_RINGTONE ).setContentType( AudioAttributes.CONTENT_TYPE_SONIFICATION ).setFlags( AudioAttributes.FLAG_AUDIBILITY_ENFORCED ).setLegacyStreamType( AudioManager.STREAM_NOTIFICATION ).build();

    notificationChannel.setSound( PushTemplateHelper.getSoundUri( context, template.getSound() ), audioAttributes );

    if( template.getLightsColor() != null )
    {
      notificationChannel.enableLights( true );
      notificationChannel.setLightColor( template.getLightsColor() | 0xFF000000 );
    }

    if( template.getVibrate() != null && template.getVibrate().length > 0 )
    {
      long[] vibrate = new long[ template.getVibrate().length ];
      int index = 0;
      for( long l : template.getVibrate() )
        vibrate[ index++ ] = l;

      notificationChannel.enableVibration( true );
      notificationChannel.setVibrationPattern( vibrate );
    }

    return notificationChannel;
  }

  static void showNotification( final Context context, final Notification notification, final String tag, final int notificationId )
  {
    final NotificationManagerCompat notificationManager = NotificationManagerCompat.from( context.getApplicationContext() );
    Handler handler = new Handler( Looper.getMainLooper() );
    handler.post( new Runnable()
    {
      @Override
      public void run()
      {
        notificationManager.notify( tag, notificationId, notification );
      }
    } );
  }
}
