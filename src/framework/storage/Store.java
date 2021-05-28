package framework.storage;

import java.io.File;
import java.util.ArrayList;

public class Store<AnyType> {

    private final AnyType object;
    private final File file;
    private final ArrayList<String> indexes;

    public Store(AnyType object, File file, String index) {
        indexes = new ArrayList<>();

        this.object = object;
        this.file = file;
        indexes.add(index);
    }

    /**
     * @return The object
     */
    public AnyType getObject() {
        return object;
    }

    /**
     * Adds an index to the object if it does not already exists
     *
     * @param index The index to be added
     * @return True if it was successfully added, false if it already existed
     */
    public boolean addIndex(String index) {
        if (indexes.contains(index)) {
            return false;
        }
        else {
            indexes.add(index);
            return true;
        }
    }

    /**
     * @param index The index searching for
     * @return True if the index does exists in this object, false if it does not
     */
    public boolean hasIndex(String index) {
        return indexes.contains(index);
    }

    /**
     * @param file The file comparing to
     * @return True if the file is matching what is stored here, false if it does not
     */
    public boolean sameFile(File file) {
        return this.file.equals(file);
    }
}
