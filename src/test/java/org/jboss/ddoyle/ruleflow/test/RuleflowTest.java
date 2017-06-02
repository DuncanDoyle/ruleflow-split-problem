package org.jboss.ddoyle.ruleflow.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

public class RuleflowTest {
	
	@Test
	public void testRuleflowWithSingleFire() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.newKieClasspathContainer();
		
		StatelessKieSession kieSession = kieContainer.newStatelessKieSession("stateless-kie-session");
		KieCommands commandFactory = kieServices.getCommands();
		
		List<Command<?>> commands = new ArrayList<>();
		commands.add(commandFactory.newInsert("Hello rules!"));
		commands.add(commandFactory.newStartProcess("test-process"));
		commands.add(commandFactory.newFireAllRules());
		
		kieSession.execute(commandFactory.newBatchExecution(commands));
	}
	
	@Test
	public void testRuleflowWithDoubleFire() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.newKieClasspathContainer();
		
		StatelessKieSession kieSession = kieContainer.newStatelessKieSession("stateless-kie-session");
		KieCommands commandFactory = kieServices.getCommands();
		
		List<Command<?>> commands = new ArrayList<>();
		commands.add(commandFactory.newInsert("Hello rules!"));
		/* 
		 * Everything works when we fire the rules twice, once before and once afte we start the process.
		 * Note that if we only fire the rules BEFORE we start the process, the rules in the rfg after the split won't fire.
		 */
		commands.add(commandFactory.newFireAllRules());
		commands.add(commandFactory.newStartProcess("test-process"));
		commands.add(commandFactory.newFireAllRules());
		
		kieSession.execute(commandFactory.newBatchExecution(commands));
	}

}
