package pl.sda.finalapp.app.categories.persistence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*Data access object*/
public class CategoryDAO {

    private static CategoryDAO INSTANCE;
    private List<CategoryFromFileDTO> categoryFromFileDTOList = populateCategories();

    private CategoryDAO() {
    }

    public List<CategoryFromFileDTO> getCategoryList() {
        return categoryFromFileDTOList;
    }

    public static CategoryDAO getInstance() {
        if (INSTANCE == null) {
            synchronized (CategoryDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CategoryDAO();
                }

            }
        }
        return INSTANCE;
    }

    private List<CategoryFromFileDTO> populateCategories() {
        List<String> categoriesList = new ArrayList<>();
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL categoriesUrl = classLoader.getResource("kategorie.txt");

        try {
            categoriesList = Files.readAllLines(Paths.get(categoriesUrl.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        List<CategoryFromFileDTO> listCategories = categoriesList.stream()
                .map(t -> CategoryFromFileDTO.applyFromCategory(t))
                .collect(Collectors.toList());

        Map<Integer, List<CategoryFromFileDTO>> categoriesMap = listCategories.stream()
                .collect(Collectors.groupingBy(e -> e.getDepth()));

//        Map<Integer, List<Category>> categoriesMap = new HashMap<>();
//        listCategories.stream();
//        for (Category lC : listCategories) {
//            if(categoriesMap.containsKey(lC.getDepth())){
//                List<Category> innerList = categoriesMap.get(lC.getDepth());
//                innerList.add(lC);
//            } else {
//                List<Category> innerList = new ArrayList<>();
//                innerList.add(lC);
//                categoriesMap.put(lC.getDepth(), innerList);
//            }
//        }

        populateParentId(1, categoriesMap);

        return listCategories;
    }

    private void populateParentId(int depth, Map<Integer, List<CategoryFromFileDTO>> categoriesMap) {
        List<CategoryFromFileDTO> children = categoriesMap.get(depth);
        List<CategoryFromFileDTO> parents = categoriesMap.get(depth - 1);

        if (children == null) {
            return;
        }

        for (CategoryFromFileDTO child : children) {
            choseParentId(parents, child);
        }

        populateParentId(depth + 1, categoriesMap);
    }

    private void choseParentId(List<CategoryFromFileDTO> parents, CategoryFromFileDTO child) {
        Integer childId = child.getId();
        Integer parentId = parents.stream()
                .map(e -> e.getId())
                .filter(id -> id < childId)
                .sorted((a, b) -> b - a)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parent not found."));

        child.setParentId(parentId);


    }

}