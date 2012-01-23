// Automatically Generated -- DO NOT EDIT
// com.binomed.client.requestfactory.MyRequestFactory
package com.binomed.client.requestfactory;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class MyRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("rKhXmHgs8UJ9mZV82Ch6coBcx84="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Lcom/binomed/server/requestfactory/RequestFactoryObjectA;")
  .withMethodName("getMessage")
  .withRequestContext("com.binomed.client.requestfactory.MyRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("m5ol$9B_jGw3tbTAttdYFHSO0j0="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Lcom/binomed/client/requestfactory/shared/RequestFactoryObjectBProxy;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Lcom/binomed/server/requestfactory/RequestFactoryObjectB;)Lcom/binomed/server/requestfactory/RequestFactoryObjectA;")
  .withMethodName("getMessageWithParameter")
  .withRequestContext("com.binomed.client.requestfactory.MyRequestFactory$HelloWorldRequest")
  .build());
withRawTypeToken("dAJixdMLXbKVK06iqNXls4_iD2Y=", "com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy");
withRawTypeToken("9l4kO1Z7VoA_XKtQ67ANRd$Bt2s=", "com.binomed.client.requestfactory.shared.RequestFactoryObjectBProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("com.binomed.server.requestfactory.RequestFactoryObjectA", Arrays.asList("com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy"));
withClientToDomainMappings("com.binomed.server.requestfactory.RequestFactoryObjectB", Arrays.asList("com.binomed.client.requestfactory.shared.RequestFactoryObjectBProxy"));
}}
