package order;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses( {FirstIT.class, SecondIT.class, ThirdIT.class} )
public class TopLevelSuite {

}
