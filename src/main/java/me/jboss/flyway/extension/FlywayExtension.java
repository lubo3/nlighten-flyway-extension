package me.jboss.flyway.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.parsing.ExtensionParsingContext;


/**
 * An Flyway extension to the Wildfly Application Server. Initialize this extension and XML parser.
 * 
 * @author lubo
 */
public class FlywayExtension implements Extension {

  /** The name space used for the {@code subsystem} element. */
  public static final String NAMESPACE = "urn:me.jboss.flyway:1.0";

  /** The name of our subsystem within the model. */
  public static final String SUBSYSTEM_NAME = "flyway";

  /** The Constant RESOURCE_NAME. */
  private static final String RESOURCE_NAME =
      FlywayExtension.class.getPackage().getName() + ".LocalDescriptions";

  /** The Constant PATH. */
  protected static final String PATH = "path";

  /** The Constant SUBSYSTEM_PATH. */
  protected static final PathElement SUBSYSTEM_PATH =
      PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);

  /**
   * Gets the resource description resolver.
   *
   * @param keyPrefix the key prefix
   * @return the resource description resolver
   */
  public static StandardResourceDescriptionResolver getResourceDescriptionResolver(
      final String keyPrefix) {
    String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
    return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME,
        FlywayExtension.class.getClassLoader(), true, false);
  }

  /** {@inheritDoc} */
  @Override
  public void initializeParsers(ExtensionParsingContext context) {
    context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, new FlywayParser());
  }

  /** {@inheritDoc} */
  @Override
  public void initialize(ExtensionContext context) {
    final SubsystemRegistration subsystem =
        context.registerSubsystem(SUBSYSTEM_NAME, ModelVersion.create(1, 0, 0));
    subsystem.registerSubsystemModel(FlywayRootDefinition.INSTANCE);
    subsystem.registerXMLElementWriter(new FlywayParser());
  }
}
