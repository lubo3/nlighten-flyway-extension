package me.jboss.flyway.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 * The Class FlywayRootDefinition.
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

  /** {@inheritDoc} */
  @Override
  protected List<? extends PersistentResourceDefinition> getChildren() {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc} Registers an add operation handler or a remove operation handler if one was
   * provided to the constructor.
   */
  @Override
  public void registerOperations(ManagementResourceRegistration resourceRegistration) {
    super.registerOperations(resourceRegistration);
    // We always need to add a 'describe' operation for root resource
    resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION,
        GenericSubsystemDescribeHandler.INSTANCE);
  }
}
