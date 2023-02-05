import com.formdev.flatlaf.FlatDarkLaf;

public class DefaultTheme
	extends FlatDarkLaf
{
	public static final String NAME = "DefaultTheme";

	public static boolean setup() {
		return setup( new DefaultTheme() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, DefaultTheme.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
