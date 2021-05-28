package framework.storage;

import java.io.File;
import java.util.ArrayList;

public class Storage<AnyType> {

    private final ArrayList<Store<AnyType>> arrayList;

    public Storage() {
        arrayList = new ArrayList<>();
    }

    /**
     * Loads an object, if the object already exits (i.e. has same filepath) the tag will try to be inserted
     *
     * @param object The object
     * @param file Filepath to the object
     * @param tag The tag/index to be uniquely identified to the object
     * @throws Exception If the tag/index already exists
     */
    public void loadObject(AnyType object, File file, String tag) throws Exception {
        String index = tag.toUpperCase();

        for (Store<AnyType> s :arrayList) {
            if (s.hasIndex(index)) {
                throw new Exception("Can't have multiple indexes with same value!");
            }
        }

        // Check if the if a identical file already exists
        for (Store<AnyType> s : arrayList) {
            if (s.sameFile(file)) {
                if (s.addIndex(index)) {    // Try to add the index
                    return;
                }
                else {
                    throw new Exception("An texture already exists with the same index!");
                }
            }
        }

        arrayList.add(new Store<>(object, file, index));
    }

    /**
     * Checks if the object exists in the storage
     *
     * @param file The filepath of the object
     * @return True if it exists or false if it does not
     */
    public boolean hasFile(File file) {
        for (Store<AnyType> s : arrayList) {
            if (s.sameFile(file)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds an additional tag/index to an object if it exits.
     *
     * @param file The filepath
     * @param tag The tag to be added
     * @return False if the index already exits. True if it was sucessfully added
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean addIndex(File file, String tag) {
        String index = tag.toUpperCase();

        for (Store<AnyType> s : arrayList) {
            if (s.hasIndex(index)) {
                return false;
            }
        }

        for (Store<AnyType> s : arrayList) {
            if (s.sameFile(file)) {
                return s.addIndex(index);
            }
        }

        return false;
    }

    /**
     * @param tag The tag to find the object searching for
     * @return The object if it is found or null if it does not exits
     */
    public AnyType getObject(String tag) {
        String index = tag.toUpperCase();

        for (Store<AnyType> s : arrayList) {
            if (s.hasIndex(index)) {
                return s.getObject();
            }
        }

        return null;    // Did not found anything :(
    }

}
