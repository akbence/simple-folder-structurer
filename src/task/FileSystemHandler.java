package task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.TreeItem;

public class FileSystemHandler {

    /**
     * Returns the writable folder structure based on readable and writable folder list for a given user.
     * 
     * @param readableFolders
     * @param writableFolders
     * @return
     */
    public TreeItem getWritableFolderStructure(List<String> readableFolders, List<String> writableFolders) {

        List<String> avaliableFolders = writableFolders.stream().filter(folder -> checkIfAvaliable(folder, readableFolders))
                .collect(Collectors.toList());

        return makeTreeFromAllowed(avaliableFolders);
    }

    private boolean checkIfAvaliable(String path, List<String> readableFolders) {
        if (readableFolders.contains(path)) {
            int lastPer = path.lastIndexOf("/");
            if (lastPer == 0) {
                return true;
            }
            return checkIfAvaliable(path.substring(0, lastPer), readableFolders);
        }
        return false;
    }

    private TreeItem makeTreeFromAllowed(List<String> writableFolderPaths) {
        TreeItem root = new TreeItem();
        root.setName("/"); // root
        root.setChildren(new ArrayList<>());
        for (String path : writableFolderPaths) {
            findAndAddFolder(root, path);
        }
        return root;
    }

    private void findAndAddFolder(TreeItem parent, String path) {
        if (parent.getName().equals(path)) {
            return;
        }
        int parentLength = parent.getName().length() - 1;
        String relativeName = path.substring(parentLength).split("/")[1];
        String divider = "/";
        if (parent.getName().equals("/")) {
            divider = "";
        }
        String currentLevelName = parent.getName() + divider + relativeName.split("/")[0];
        Optional<TreeItem> folderOptional = parent.getChildren().stream().filter(child -> child.getName().equals(currentLevelName)).findFirst();
        if (folderOptional.isEmpty()) {
            TreeItem folder = new TreeItem();
            folder.setName(parent.getName() + divider + relativeName);
            folder.setChildren(new ArrayList<>());
            parent.getChildren().add(folder);
            if (folder.getName().equals(path)) {
                return;
            } else {
                findAndAddFolder(folder, path);
            }
        } else {
            findAndAddFolder(folderOptional.get(), path);
        }
    }
}
