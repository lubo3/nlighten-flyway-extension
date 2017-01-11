package me.jboss.flyway.extension;

import java.util.Collection;
import java.util.Collections;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;

/**
 * The FlywayRootDefinition class is a resource definition.
 * 
 * @author lubo
 */
public class FlywayRootDefinition extends PersistentResourceDefinition {

  /** The Constant INSTANCE. */
  public static final FlywayRootDefinition INSTANCE = new FlywayRootDefinition();

  /**
   * Instantiates a new flyway root definition.
   */
  private FlywayRootDefinition() {
    super(FlywayExtension.SUBSYSTEM_PATH, FlywayExtension.getResourceDescriptionResolver(null),
        // We always need to add an 'add' operation
        FlywaySubsystemAdd.INSTANCE,
        // Every resource that is added, normally needs a remove operation
        ReloadRequiredRemoveStepHandler.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  public Collection<AttributeDefinition> getAttributes() {
    return Collections.emptyList();
  }
}
