package pl.sda.finalapp.app.categories.persistence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/*Data access object*/
public class CategoryDAO {

    private static CategoryDAO INSTANCE;
    private List<Category> categoryList = populateCategories();

    private CategoryDAO() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
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

    private List<Category> populateCategories() {
        List<String> categoriesList = new ArrayList<>();
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL categoriesUrl = classLoader.getResource("categories.txt");

        try {
            categoriesList = Files.readAllLines(Paths.get(categoriesUrl.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        List<Category> listCategories = categoriesList.stream()
                .map(t -> Category.applyFromCategory(t))
                .collect(Collectors.toList());

        Map<Integer, List<Category>> categoriesMap = listCategories.stream()
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

    private void populateParentId(int depth, Map<Integer, List<Category>> categoriesMap) {
        List<Category> children = categoriesMap.get(depth);
        List<Category> parents = categoriesMap.get(depth - 1);

        if (children == null) {
            return;
        }

        for (Category child : children) {
            choseParentId(parents, child);
        }

        populateParentId(depth + 1, categoriesMap);
    }

    private void choseParentId(List<Category> parents, Category child) {
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