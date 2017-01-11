package me.jboss.flyway.exception;

/**
 * The Class FlywayException.
 * 
 * @author lubo
 */
public class FlywayException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6448231271266604598L;

  /**
   * The message key.
   */
  private Enum<?> messageKey;

  /**
   * The arguments for the message.
   */
  private Object[] arguments;

  /**
   * Instantiates a new flyway exception.
   */
  public FlywayException() {
    super();
  }

  public FlywayException(Enum<?> messageKey, Object... arguments) {
    this.messageKey = messageKey;
    this.arguments = arguments;
  }

  public Enum<?> getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(Enum<?> messageKey) {
    this.messageKey = messageKey;
  }

  public Object[] getArguments() {
    return arguments;
  }

  public void setArguments(Object[] arguments) {
    this.arguments = arguments;
  }
}
