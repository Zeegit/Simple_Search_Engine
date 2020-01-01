package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Peoples {
    ArrayList<String> peoples;
    Map<String, List<Integer>> index;

    public Peoples() {
        peoples = new ArrayList<>();
        index = new HashMap<String, List<Integer>>();
    }

    public void loadFromFile(String fileName) {
        File file = new File(fileName);
        try (Scanner scn = new Scanner(file)) {
            while (scn.hasNext()) {
                peoples.add(scn.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        createIndex();
    }

    private void createIndex() {
        for(int i=0; i < peoples.size(); i++) {
            for (String _word : peoples.get(i).split("\\s+")) {
                String word = _word.toLowerCase();

                List<Integer> idx = index.get(word);

                if (idx == null) {
                    idx = new ArrayList<Integer>();
                    index.put(word, idx);
                }
                idx.add(i);
            }
        }
        // System.out.println(index);
    }

    public String[] getAllPeoples() {
        String[] p = new String[peoples.size()];
        for (int i = 0; i < peoples.size(); i++) {
            p[i] = peoples.get(i);
        }
        return p;
    }

    public String[] getSelectedPeoples(String text) {
        ArrayList<String> f = new ArrayList<>();
        ;
        for (int i = 0; i < peoples.size(); i++) {
            if (peoples.get(i).toUpperCase().contains(text)) {
                f.add(peoples.get(i));
            }
        }

        String[] p = new String[f.size()];
        for (int i = 0; i < f.size(); i++) {
            p[i] = f.get(i);
        }
        return p;
    }

    public String[] getSelectedPeoplesByIndex(String text) {
        String word = text.toLowerCase();

        List<Integer> idx = index.get(word);
        String[] p = null;
        if (idx != null) {
            p = new String[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                p[i] = peoples.get(idx.get(i));
            }
        }

        return p;
    }

    public String[] getSelectedPeoples_ANY(String text) {
        List<Integer> idx = new ArrayList<>();
        Set<Integer> s = new HashSet<>();

        String[] words = text.split("\\s+");
        for(String word: words) {
            List<Integer> part = index.get(word.toLowerCase());
            if (part != null) {
                s.addAll(part);
            }
        }

        idx.addAll(s);

        String[] p = null;
        if (idx != null) {
            p = new String[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                p[i] = peoples.get(idx.get(i));
            }
        }

        return p;
    }



    public String[] getSelectedPeoples_NONE(String text) {
        List<Integer> idx = new ArrayList<>();
        Set<Integer> s = new HashSet<>();

        // Все записи
        for(int i = 0; i < peoples.size(); i++) {
            idx.add(i);
        }

        String[] words = text.split("\\s+");
        for(String word: words) {
            List<Integer> part = index.get(word.toLowerCase());
            if (part != null) {
                idx.removeAll(part);
            }
        }

        String[] p = null;
        if (idx != null) {
            p = new String[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                p[i] = peoples.get(idx.get(i));
            }
        }

        return p;
    }

    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public String[] getSelectedPeoples_ALL(String text) {
        List<Integer> idx  = null;

        String[] words = text.split("\\s+");
        for(String word: words) {
            List<Integer> part = index.get(word.toLowerCase());
            if (part != null) {
                if (idx == null) {
                    idx = part;
                } else {
                    idx = intersection(part, idx);
                }
            }
        }

        String[] p = null;
        if (idx != null) {
            p = new String[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                p[i] = peoples.get(idx.get(i));
            }
        }

        return p;
    }
}
