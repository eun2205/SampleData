package example.tacademy.sampledata;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-08-11.
 */
public class AddressContract {
    public interface Address extends BaseColumns {
        public static final String TABLE = "addresstbl";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";
    }
}
