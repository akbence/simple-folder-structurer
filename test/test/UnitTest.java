package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.TreeItem;
import task.FileSystemHandler;

public class UnitTest {

    private static FileSystemHandler fileSystemHandler;
    private static List<String> readableFolders;
    private static List<String> writableFolders;

    @BeforeAll
    private static void setup() {
        fileSystemHandler = new FileSystemHandler();
        /**
         * Filesystem:
         * /var
         *      /sys
         *          /sysfolders
         *              /urw (user writeable)
         *          /sysu (user writeable)
         *      /u (user writeable)
         *          /ur
         *          /urw (user writeable)
         */
        writableFolders = Arrays.asList("/var/sys/sysfolders/urw", "/var/sys/sysu", "/var/u", "/var/u/urw");
        readableFolders = new ArrayList<>(Arrays.asList("/var", "/var/sys"));
        readableFolders.addAll(writableFolders);

    }

    @Test
    @DisplayName("testWithCompleteExampleFileSystem")
    public void testWithCompleteExampleFileSystem() {
        List<String> expectedList = Arrays.asList("/var/sys/sysu", "/var/u", "/var/u/urw");
        TreeItem tree = fileSystemHandler.getWritableFolderStructure(readableFolders, writableFolders);
        Assertions.assertAll(() -> expectedList.forEach(expected -> Assertions.assertTrue(findByPath(tree, expected))));
    }

    private boolean findByPath(TreeItem tree, String path) {
        if (tree.getName().equals(path)) {
            return true;
        } else {
            if (tree.getChildren().isEmpty()) {
                return false;
            }
            for (TreeItem child : tree.getChildren()) {
                if (findByPath(child, path)) {
                    return true;
                }
            }
            return false;
        }
    }
}
