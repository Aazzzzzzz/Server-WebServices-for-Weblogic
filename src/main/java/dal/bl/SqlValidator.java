package dal.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlValidator
{
    public static String SafeString(String item)
    {
        if (item != null)
            item = item.replace("'", "''");
        return item;
    }
}
