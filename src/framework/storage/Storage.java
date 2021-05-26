package framework.storage;

import java.io.File;
import java.util.ArrayList;

public class Storage<AnyType> {

    private final ArrayList<Store<AnyType>> arrayList;

    public Storage() {
        arrayList = new ArrayList<>();
    }

    /**
     * @param object
     * @param file
     * @param index
     * @throws Exception
     */
    public void loadObject(AnyType object, File file, String index) throws Exception {
        // Check if the if a identical file already exists or if there is already an object linking to the index.
        for (Store<AnyType> s : arrayList) {
            if (s.sameFile(file)) {
                if (s.addIndex(index)) {
                    return;
                }
                else {
                    throw new Exception("An texture already exists with the same index!");
                }
            }

            if (s.hasIndex(index)) {
                throw new Exception("Can't have multiple indexes with same value!");
            }
        }

        arrayList.add(new Store<>(object, file, index));
    }

    /**
     * @param file
     * @return
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
     * @param file
     * @param index
     * @return
     */
    public boolean addIndex(File file, String index) {
        for (Store<AnyType> s : arrayList) {
            if (s.sameFile(file)) {
                return s.addIndex(index);
            }

            if (s.hasIndex(index)) {
                return false;
            }
        }

        return false;
    }

    /**
     * @param index
     * @return
     */
    public AnyType getObject(String index) {
        for (Store<AnyType> s : arrayList) {
            if (s.hasIndex(index)) {
                return s.getObject();
            }
        }

        return null;    // Did not found anything :(
    }

}
