package de.rieckpil.learning;

import de.rieckpil.learning.decorators.SampleBean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.el.ELProcessor;
import javax.enterprise.inject.literal.NamedLiteral;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@Startup
@Singleton
public class HelloWorld {

    @Inject
    BeanManager beanManager;

    @Inject
    SampleBean sampleBean;

    @Inject
    String myString;

    @PostConstruct
    public void sayHello() {

        System.out.println(myString);

        ELProcessor elProcessor = new ELProcessor();
        elProcessor.getELManager().addELResolver(beanManager.getELResolver());

        MyService myService = (MyService) elProcessor.getValue("myService", MyService.class);
        MyService myServiceTwo = (MyService) CDI.current().select(Object.class, NamedLiteral.of("myService")).get();
        MyService myServiceThree = CDI.current().select(MyService.class, MyQualifier.Literal.INSTANCE).get();

        myService.sayHello();
        myServiceTwo.sayHello();
        myServiceThree.sayHello();

        System.out.println(sampleBean.sayHello("Philip"));
        System.out.println(sampleBean.sayGoodbye("Philip"));
    }
}
