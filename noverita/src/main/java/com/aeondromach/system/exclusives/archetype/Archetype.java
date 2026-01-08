package com.aeondromach.system.exclusives.archetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aeondromach.system.Character.StatIndex;
import com.aeondromach.system.IdClassList;
import com.aeondromach.system.abstracts.Exclusive;

import javafx.scene.image.Image;

public class Archetype extends Exclusive {
    private String name;
    private String description;
    private String ascendantTitle;
    private Image image;
    private StatIndex recommendedModifier; // Which stat will influence abilities? Similar to spell casting ability in DnD.

    private String id;
    private int rank;

    private ArrayList<Ability> abilityList;
    private ArrayList<Edict> edictList;

    private final Map<String, StatIndex> statChecker = createStatChecker();

    private static Map<String, StatIndex> createStatChecker() {
        Map<String, StatIndex> map = new HashMap<>();

        map.put("strength", StatIndex.STRENGTH);
        map.put("str", StatIndex.STRENGTH);
        map.put("1", StatIndex.STRENGTH);

        map.put("dexterity", StatIndex.DEXTERITY);
        map.put("dex", StatIndex.DEXTERITY);
        map.put("2", StatIndex.DEXTERITY);

        map.put("constitution", StatIndex.CONSTITUTION);
        map.put("con", StatIndex.CONSTITUTION);
        map.put("3", StatIndex.CONSTITUTION);

        map.put("intelligence", StatIndex.INTELLIGENCE);
        map.put("int", StatIndex.INTELLIGENCE);
        map.put("4", StatIndex.INTELLIGENCE);

        map.put("wisdom", StatIndex.WISDOM);
        map.put("wis", StatIndex.WISDOM);
        map.put("5", StatIndex.WISDOM);

        map.put("charisma", StatIndex.CHARISMA);
        map.put("cha", StatIndex.CHARISMA);
        map.put("6", StatIndex.CHARISMA);

        return map;
    }

    public Archetype(String id, int rank) {
        setMapSignifier(IdClassList.IdType.ARCHETYPE);
        this.id = id;
        this.rank = rank;

        this.grantList = null;
        this.statList = null;

        this.abilityList = null;
        this.edictList = null;

        checkId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAscendantTitle(String ascendantTitle) {
        this.ascendantTitle = ascendantTitle;
    }

    public String getAscendantTitle() {
        return ascendantTitle;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    protected void resetUniqueStats() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetUniqueStats'");
    }

    @Override
    protected void parseXML(Document doc) {
        if (!abilityList.isEmpty() || !this.abilityList.isEmpty()) abilityList.clear();
        if (!edictList.isEmpty() || !this.edictList.isEmpty()) edictList.clear();

        this.grantList.clear();
        this.statList.clear();
        this.abilityList.clear();
        this.edictList.clear();

        if (doc != null) {
            Elements elements = doc.select("element[id]");
            for (Element element: elements) {
                if (element.attr("id").equals(this.id) && element.attr("type").toLowerCase().equals("archetype") && !element.attr("name").isEmpty() && !element.select("description").isEmpty()) {
                    this.name = element.attr("name");
                    this.description = element.selectFirst("description").text();
                    if (!element.attr("ascendanttitle").isEmpty()) this.ascendantTitle = element.selectFirst("ascendanttitle").text();
                        else this.ascendantTitle = "Ascendant " + this.name;

                    if (!element.attr("recommendedmodifier").isEmpty()) {
                        this.recommendedModifier =
                            statChecker.getOrDefault(
                                element.attr("recommendedmodifier").toLowerCase(),
                                StatIndex.WISDOM
                            );
                    }
                    else {
                        this.recommendedModifier = StatIndex.WISDOM;
                    }
                    

                    Elements abilities = element.select("ability");
                    for (Element abilityElement: abilities) {
                        Ability ability = new Ability(abilityElement.attr("id"));
                        this.abilityList.add(ability);
                    }

                    Elements edicts = element.select("edict");
                    for (Element edictElement: edicts) {
                        Edict edict = new Edict(edictElement.attr("id"));
                    }
                }
            }
        }
    }
}