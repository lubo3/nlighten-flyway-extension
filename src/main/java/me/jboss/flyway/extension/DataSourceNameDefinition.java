package me.jboss.flyway.extension;

import static me.jboss.flyway.extension.FlywayExtension.TYPE;
import static me.jboss.flyway.extension.FlywayExtension.TYPE_PATH;

import java.util.Collection;
import java.util.Collections;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

public class DataSourceNameDefinition extends PersistentResourceDefinition {

  private static String DEFAULT_DATASOURCE_NAME = "postgres";

  protected static final AttributeDefinition PATH =
      new SimpleAttributeDefinitionBuilder(FlywayExtension.PATH, ModelType.STRING)
          .setAllowExpression(true).setDefaultValue(new ModelNode(DEFAULT_DATASOURCE_NAME))
          .setAllowNull(false).build();

  public static final DataSourceNameDefinition INSTANCE = new DataSourceNameDefinition();

  private DataSourceNameDefinition() {
    super(TYPE_PATH, FlywayExtension.getResourceDescriptionResolver(TYPE),
        // We always need to add an 'add' operation
        DataSourceNameAdd.INSTANCE,
        // Every resource that is added, normally needs a remove operation
        DataSourceNameRemove.INSTANCE);
  }

  @Override
  public Collection<AttributeDefinition> getAttributes() {
    return Collections.singleton(PATH);
  }


  @Override
  public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
    // resourceRegistration.registerReadWriteAttribute(TICK, null, FlywayTickHandler.INSTANCE);
  }
}
