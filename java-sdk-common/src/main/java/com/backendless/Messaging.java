package com.backendless;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.messaging.BodyParts;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.EmailEnvelope;
import com.backendless.messaging.Message;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.DeviceRegistrationResult;
import com.backendless.rt.messaging.Channel;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface Messaging
{
  static String getDeviceId()
  {
    return GeneralMessaging.prefs.getDeviceId();
  }

  static String getOS()
  {
    return GeneralMessaging.prefs.getOs();
  }

  static String getOsVersion()
  {
    return GeneralMessaging.prefs.getOsVersion();
  }

  void registerDevice();

  void registerDevice( AsyncCallback<DeviceRegistrationResult> callback );

  void registerDevice( List<String> channels );

  void registerDevice( List<String> channels, Date expiration );

  void registerDevice( List<String> channels, AsyncCallback<DeviceRegistrationResult> callback );

  void registerDevice( List<String> channels, Date expiration, AsyncCallback<DeviceRegistrationResult> callback );

  void unregisterDevice();

  void unregisterDevice( List<String> channels );

  void unregisterDevice( AsyncCallback<Integer> callback );

  void unregisterDevice( final List<String> channels, final AsyncCallback<Integer> callback );

  boolean refreshDeviceToken( String newDeviceToken );

  void refreshDeviceToken( String newDeviceToken, final AsyncCallback<Boolean> responder );

  DeviceRegistration getDeviceRegistration();

  DeviceRegistration getRegistrations();

  void getDeviceRegistration( AsyncCallback<DeviceRegistration> responder );

  void getRegistrations( AsyncCallback<DeviceRegistration> responder );

  /**
   * Publishes message to "default" channel. The message is not a push notification, it does not have any headers and
   * does not go into any subtopics.
   *
   * @param message object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                user-defined complex type, a collection or an array of these types.
   * @return a data structure which contains ID of the published message and the status of the publish operation.
   * @throws BackendlessException
   */
  MessageStatus publish( Object message );

  /**
   * Publishes message to specified channel. The message is not a push notification, it does not have any headers and
   * does not go into any subtopics.
   *
   * @param channelName name of a channel to publish the message to. If the channel does not exist, Backendless
   *                    automatically creates it.
   * @param message     object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                    user-defined complex type, a collection or an array of these types.
   * @return ${@link com.backendless.messaging.MessageStatus} - a data structure which contains ID of the published
   * message and the status of the publish operation.
   * @throws BackendlessException
   */
  MessageStatus publish( String channelName, Object message );

  /**
   * Publishes message to specified channel. The message is not a push notification, it may have headers and/or subtopic
   * defined in the publishOptions argument.
   *
   * @param channelName    name of a channel to publish the message to. If the channel does not exist, Backendless
   *                       automatically creates it.
   * @param message        object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                       user-defined complex type, a collection or an array of these types.
   * @param publishOptions an instance of ${@link PublishOptions}. When provided may contain
   *                       publisher ID (an arbitrary, application-specific string value identifying the publisher),
   *                       subtopic value and/or a collection of headers.
   * @return a data structure which contains ID of the published message and the status of the publish operation.
   */
  MessageStatus publish( String channelName, Object message, PublishOptions publishOptions );

  /**
   * Publishes message to specified channel.The message may be configured as a push notification. It may have headers
   * and/or subtopic defined in the publishOptions argument.
   *
   * @param channelName     name of a channel to publish the message to. If the channel does not exist, Backendless
   *                        automatically creates it.
   * @param message         object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                        user-defined complex type, a collection or an array of these types.
   * @param publishOptions  an instance of ${@link PublishOptions}. When provided may contain
   *                        publisher ID (an arbitrary, application-specific string value identifying the publisher),
   *                        subtopic value and/or a collection of headers.
   * @param deliveryOptions an instance of ${@link DeliveryOptions}. When provided may specify
   *                        options for message delivery such as: deliver as a push notification, deliver to specific
   *                        devices (or a group of devices grouped by the operating system), delayed delivery or repeated
   *                        delivery.
   * @return a data structure which contains ID of the published message and the status of the publish operation.
   */
  MessageStatus publish( String channelName, Object message, PublishOptions publishOptions, DeliveryOptions deliveryOptions );

  /**
   * Publishes message to "default" channel. The message is not a push notification, it does not have any headers and
   * does not go into any subtopics.
   *
   * @param message        object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                       user-defined complex type, a collection or an array of these types.
   * @param publishOptions an instance of ${@link PublishOptions}. When provided may contain
   *                       publisher ID (an arbitrary, application-specific string value identifying the publisher),
   *                       subtopic value and/or a collection of headers.
   * @return a data structure which contains ID of the published message and the status of the publish operation.
   * @throws BackendlessException
   */
  MessageStatus publish( Object message, PublishOptions publishOptions );

  /**
   * Publishes message to "default" channel.The message may be configured as a push notification. It may have headers
   * and/or subtopic defined in the publishOptions argument.
   *
   * @param message         object to publish. The object can be of any data type - a primitive value, String, Date, a
   *                        user-defined complex type, a collection or an array of these types.
   * @param publishOptions  an instance of ${@link PublishOptions}. When provided may contain
   *                        publisher ID (an arbitrary, application-specific string value identifying the publisher),
   *                        subtopic value and/or a collection of headers.
   * @param deliveryOptions an instance of ${@link DeliveryOptions}. When provided may specify
   *                        options for message delivery such as: deliver as a push notification, deliver to specific
   *                        devices (or a group of devices grouped by the operating system), delayed delivery or repeated
   *                        delivery.
   * @return a data structure which contains ID of the published message and the status of the publish operation.
   */
  MessageStatus publish( Object message, PublishOptions publishOptions, DeliveryOptions deliveryOptions );

  void publish( Object message, final AsyncCallback<MessageStatus> responder );

  void publish( String channelName, Object message, final AsyncCallback<MessageStatus> responder );

  void publish( String channelName, Object message, PublishOptions publishOptions, final AsyncCallback<MessageStatus> responder );

  void publish( String channelName, Object message, PublishOptions publishOptions, DeliveryOptions deliveryOptions,
                final AsyncCallback<MessageStatus> responder );

  void publish( Object message, PublishOptions publishOptions, final AsyncCallback<MessageStatus> responder );

  void publish( Object message, PublishOptions publishOptions, DeliveryOptions deliveryOptions,
                final AsyncCallback<MessageStatus> responder );

  MessageStatus pushWithTemplate( String templateName );

  MessageStatus pushWithTemplate( String templateName, Map<String, String> templateValues );

  void pushWithTemplate( String templateName, final AsyncCallback<MessageStatus> responder );

  void pushWithTemplate( String templateName, Map<String, String> templateValues, final AsyncCallback<MessageStatus> responder );

  MessageStatus getMessageStatus( String messageId );

  void getMessageStatus( String messageId, AsyncCallback<MessageStatus> responder );

  boolean cancel( String messageId );

  void cancel( String messageId, AsyncCallback<MessageStatus> responder );

  Channel subscribe();

  Channel subscribe( String channelName );

  List<Message> pollMessages( String channelName, String subscriptionId );

  MessageStatus sendTextEmail( String subject, String messageBody, List<String> recipients );

  MessageStatus sendTextEmail( String subject, String messageBody, String recipient );

  MessageStatus sendHTMLEmail( String subject, String messageBody, List<String> recipients );

  MessageStatus sendHTMLEmail( String subject, String messageBody, String recipient );

  MessageStatus sendEmail( String subject, BodyParts bodyParts, String recipient, List<String> attachments );

  MessageStatus sendEmail( String subject, BodyParts bodyParts, String recipient );

  MessageStatus sendEmail( String subject, BodyParts bodyParts, List<String> recipients, List<String> attachments );

  void sendTextEmail( String subject, String messageBody, List<String> recipients, final AsyncCallback<MessageStatus> responder );

  void sendTextEmail( String subject, String messageBody, String recipient, final AsyncCallback<MessageStatus> responder );

  void sendHTMLEmail( String subject, String messageBody, List<String> recipients, final AsyncCallback<MessageStatus> responder );

  void sendHTMLEmail( String subject, String messageBody, String recipient, final AsyncCallback<MessageStatus> responder );

  void sendEmail( String subject, BodyParts bodyParts, String recipient, List<String> attachments,
                  final AsyncCallback<MessageStatus> responder );

  void sendEmail( String subject, BodyParts bodyParts, String recipient, final AsyncCallback<MessageStatus> responder );

  void sendEmail( String subject, BodyParts bodyParts, List<String> recipients, List<String> attachments,
                  final AsyncCallback<MessageStatus> responder );

  MessageStatus sendEmailFromTemplate( String templateName, EmailEnvelope envelope );

  MessageStatus sendEmailFromTemplate( String templateName, EmailEnvelope envelope, Map<String, String> templateValues );

  MessageStatus sendEmailFromTemplate( String templateName, EmailEnvelope envelope, List<String> attachments );

  MessageStatus sendEmailFromTemplate( String templateName, EmailEnvelope envelope, Map<String, String> templateValues,
                                       List<String> attachments );

  void sendEmailFromTemplate( String templateName, EmailEnvelope envelope, AsyncCallback<MessageStatus> responder );

  void sendEmailFromTemplate( String templateName, EmailEnvelope envelope, Map<String, String> templateValues,
                              AsyncCallback<MessageStatus> responder );

  void sendEmailFromTemplate( String templateName, EmailEnvelope envelope, List<String> attachments,
                              AsyncCallback<MessageStatus> responder );

  void sendEmailFromTemplate( String templateName, EmailEnvelope envelope, Map<String, String> templateValues, List<String> attachments,
                              AsyncCallback<MessageStatus> responder );
}
