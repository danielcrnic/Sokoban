package framework.storage;

import java.io.File;
import java.util.ArrayList;

public class Store<AnyType> {

    private AnyType object;
    private File file;
    private ArrayList<String> indexes;

    public Store(AnyType object, File file, String index) {
        indexes = new ArrayList<>();

        this.object = object;
        this.file = file;
        indexes.add(index);
    }

    public AnyType getObject() {
        return object;
    }

    public boolean addIndex(String index) {
        if (indexes.contains(index)) {
            return false;
        }
        else {
            indexes.add(index);
            return true;
        }
    }

    public boolean hasIndex(String index) {
        return indexes.contains(index);
    }

    public boolean sameFile(File file) {
        return this.file.equals(file);
    }
}
