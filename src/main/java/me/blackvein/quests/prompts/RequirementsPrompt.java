package me.blackvein.quests.prompts;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import me.blackvein.quests.CustomRequirement;
import me.blackvein.quests.Quest;
import me.blackvein.quests.QuestFactory;
import me.blackvein.quests.Quests;
import me.blackvein.quests.util.CK;
import me.blackvein.quests.util.ColorUtil;
import me.blackvein.quests.util.ItemUtil;
import me.blackvein.quests.util.Lang;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.inventory.ItemStack;

public class RequirementsPrompt extends FixedSetPrompt implements ColorUtil {

    Quests quests;
    final QuestFactory factory;

    public RequirementsPrompt(Quests plugin, QuestFactory qf) {

        super("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        quests = plugin;
        factory = qf;

    }

    @Override
    public String getPromptText(ConversationContext context) {

        String text;

        text = DARKAQUA + "- " + AQUA + context.getSessionData(CK.Q_NAME) + AQUA + " | Requirements -\n";

        if (context.getSessionData(CK.REQ_MONEY) == null) {
            text += BLUE + "" + BOLD + "1" + RESET + YELLOW + " - Set money requirement " + GRAY + " (" + Lang.get("noneSet") + ")\n";
        } else {
            int moneyReq = (Integer) context.getSessionData(CK.REQ_MONEY);
            text += BLUE + "" + BOLD + "1" + RESET + YELLOW + " - Set money requirement (" + moneyReq + " " + (moneyReq > 1 ? Quests.getCurrency(true) : Quests.getCurrency(false)) + ")\n";
        }

        if (context.getSessionData(CK.REQ_QUEST_POINTS) == null) {
            text += BLUE + "" + BOLD + "2" + RESET + YELLOW + " - Set Quest Points requirement " + GRAY + " (" + Lang.get("noneSet") + ")\n";
        } else {
            text += BLUE + "" + BOLD + "2" + RESET + YELLOW + " - Set Quest Points requirement " + GRAY + "(" + AQUA + context.getSessionData(CK.REQ_QUEST_POINTS) + " Quest Points" + GRAY + ")\n";
        }

        text += BLUE + "" + BOLD + "3" + RESET + YELLOW + " - Set item requirements\n";

        if (context.getSessionData(CK.REQ_PERMISSION) == null) {
            text += BLUE + "" + BOLD + "4" + RESET + YELLOW + " - Set permission requirements " + GRAY + " (" + Lang.get("noneSet") + ")\n";
        } else {
            text += BLUE + "" + BOLD + "4" + RESET + YELLOW + " - Set permission requirements\n";
            List<String> perms = (List<String>) context.getSessionData(CK.REQ_PERMISSION);

            for (String s : perms) {

                text += GRAY + "    - " + AQUA + s + "\n";

            }
        }

        if (context.getSessionData(CK.REQ_QUEST) == null) {
            text += BLUE + "" + BOLD + "5" + RESET + YELLOW + " - Set Quest requirements " + GRAY + " (" + Lang.get("noneSet") + ")\n";
        } else {
            text += BLUE + "" + BOLD + "5" + RESET + YELLOW + " - Set Quest requirements\n";
            List<String> qs = (List<String>) context.getSessionData(CK.REQ_QUEST);

            for (String s : qs) {

                text += GRAY + "    - " + AQUA + s + "\n";

            }
        }

        if (context.getSessionData(CK.REQ_QUEST_BLOCK) == null) {
            text += BLUE + "" + BOLD + "6" + RESET + YELLOW + " - Set Quest blocks " + GRAY + " (" + Lang.get("noneSet") + ")\n";
        } else {
            text += BLUE + "" + BOLD + "6" + RESET + YELLOW + " - Set Quest blocks\n";
            List<String> qs = (List<String>) context.getSessionData(CK.REQ_QUEST_BLOCK);

            for (String s : qs) {

                text += GRAY + "    - " + AQUA + s + "\n";

            }
        }

        if (context.getSessionData(CK.REQ_CUSTOM) == null) {
            text += BLUE + "" + BOLD + "9 - " + RESET + ITALIC + PURPLE + "Custom Requirements (None set)\n";
        } else {
            text += BLUE + "" + BOLD + "9 - " + RESET + ITALIC + PURPLE + "Custom Requirements\n";
            LinkedList<String> customReqs = (LinkedList<String>) context.getSessionData(CK.REQ_CUSTOM);
            for(String s : customReqs){
                
                text += RESET + "" + PURPLE + "  - " + PINK + s + "\n";
                
            }
        }

        if (context.getSessionData(CK.REQ_MONEY) == null && context.getSessionData(CK.REQ_QUEST_POINTS) == null && context.getSessionData(CK.REQ_QUEST_BLOCK) == null && context.getSessionData(CK.REQ_ITEMS) == null && context.getSessionData(CK.REQ_PERMISSION) == null && context.getSessionData(CK.REQ_QUEST) == null && context.getSessionData(CK.REQ_QUEST_BLOCK) == null && context.getSessionData(CK.REQ_MCMMO_SKILLS) == null && context.getSessionData(CK.REQ_HEROES_PRIMARY_CLASS) == null && context.getSessionData(CK.REQ_HEROES_SECONDARY_CLASS) == null && context.getSessionData(CK.REQ_CUSTOM) == null) {
            text += GRAY + "" + BOLD + "10 - " + RESET + GRAY + "Set fail requirements message (No requirements set)\n";
        } else if (context.getSessionData(CK.Q_FAIL_MESSAGE) == null) {
            text += RED + "" + BOLD + "10 - " + RESET + RED + "Set fail requirements message (Required)\n";
        } else {
            text += BLUE + "" + BOLD + "10 - " + RESET + YELLOW + "Set fail requirements message" + GRAY + "(" + AQUA + "\"" + context.getSessionData(CK.Q_FAIL_MESSAGE) + "\"" + GRAY + ")\n";
        }

        text += GREEN + "" + BOLD + "11" + RESET + YELLOW + " - Done";

        return text;

    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {

        if (input.equalsIgnoreCase("1")) {
            return new MoneyPrompt();
        } else if (input.equalsIgnoreCase("2")) {
            return new QuestPointsPrompt();
        } else if (input.equalsIgnoreCase("3")) {
            return new ItemListPrompt();
        } else if (input.equalsIgnoreCase("4")) {
            return new PermissionsPrompt();
        } else if (input.equalsIgnoreCase("5")) {
            return new QuestListPrompt(true);
        } else if (input.equalsIgnoreCase("6")) {
            return new QuestListPrompt(false);
        } else if (input.equalsIgnoreCase("9")) {
            return new CustomRequirementsPrompt();
        } else if (input.equalsIgnoreCase("10")) {
            return new FailMessagePrompt();
        } else if (input.equalsIgnoreCase("11")) {
            if (context.getSessionData(CK.REQ_MONEY) != null || context.getSessionData(CK.REQ_QUEST_POINTS) != null || context.getSessionData(CK.REQ_ITEMS) != null || context.getSessionData(CK.REQ_PERMISSION) != null || context.getSessionData(CK.REQ_QUEST) != null || context.getSessionData(CK.REQ_QUEST_BLOCK) != null || context.getSessionData(CK.REQ_MCMMO_SKILLS) != null || context.getSessionData(CK.REQ_HEROES_PRIMARY_CLASS) != null || context.getSessionData(CK.REQ_HEROES_SECONDARY_CLASS) != null || context.getSessionData(CK.REQ_CUSTOM) != null) {

                if (context.getSessionData(CK.Q_FAIL_MESSAGE) == null) {
                    context.getForWhom().sendRawMessage(RED + "You must set a fail requirements message!");
                    return new RequirementsPrompt(quests, factory);
                }

            }

            return factory.returnToMenu();
        }
        return null;

    }

    private class MoneyPrompt extends NumericPrompt {

        @Override
        public String getPromptText(ConversationContext context) {

            return YELLOW + "Enter amount of " + PURPLE + ((Quests.economy.currencyNamePlural().isEmpty() ? "Money" : Quests.economy.currencyNamePlural())) + YELLOW + ", or 0 to clear the money requirement, or -1 to cancel";

        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, Number input) {

            if (input.intValue() < -1) {
                context.getForWhom().sendRawMessage(RED + "Amount must be greater than 0!");
                return new MoneyPrompt();
            } else if (input.intValue() == -1) {
                return new RequirementsPrompt(quests, factory);
            } else if (input.intValue() == 0) {
                context.setSessionData(CK.REQ_MONEY, null);
                return new RequirementsPrompt(quests, factory);
            }

            context.setSessionData(CK.REQ_MONEY, input.intValue());
            return new RequirementsPrompt(quests, factory);

        }
    }

    private class QuestPointsPrompt extends NumericPrompt {

        @Override
        public String getPromptText(ConversationContext context) {

            return YELLOW + "Enter amount of Quest Points, or 0 to clear the Quest Point requirement,\nor -1 to cancel";

        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, Number input) {

            if (input.intValue() < -1) {
                context.getForWhom().sendRawMessage(RED + "Amount must be greater than 0!");
                return new QuestPointsPrompt();
            } else if (input.intValue() == -1) {
                return new RequirementsPrompt(quests, factory);
            } else if (input.intValue() == 0) {
                context.setSessionData(CK.REQ_QUEST_POINTS, null);
                return new RequirementsPrompt(quests, factory);
            }

            context.setSessionData(CK.REQ_QUEST_POINTS, input.intValue());
            return new RequirementsPrompt(quests, factory);

        }
    }

    private class QuestListPrompt extends StringPrompt {

        private final boolean isRequiredQuest;

        /*public QuestListPrompt() {
         this.isRequiredQuest = true;
         }*/
        public QuestListPrompt(boolean isRequired) {
            this.isRequiredQuest = isRequired;
        }

        @Override
        public String getPromptText(ConversationContext context) {

            String text = PINK + "- Quests -\n" + PURPLE;

            boolean none = true;
            for (Quest q : quests.getQuests()) {

                text += q.getName() + ", ";
                none = false;

            }

            if (none) {
                text += "(None)\n";
            } else {
                text = text.substring(0, (text.length() - 2));
                text += "\n";
            }

            text += YELLOW + "Enter a list of Quest names separating each one by a " + RED + BOLD + "comma" + RESET + YELLOW + ", or enter \'clear\' to clear the list, or \'cancel\' to return.";

            return text;

        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("cancel") == false && input.equalsIgnoreCase("clear") == false) {

                String[] args = input.split(",");
                LinkedList<String> questNames = new LinkedList<String>();

                for (String s : args) {

                    if (quests.getQuest(s) == null) {

                        context.getForWhom().sendRawMessage(PINK + s + " " + RED + "is not a Quest name!");
                        return new QuestListPrompt(isRequiredQuest);

                    }

                    if (questNames.contains(s)) {

                        context.getForWhom().sendRawMessage(RED + "List contains duplicates!");
                        return new QuestListPrompt(isRequiredQuest);

                    }

                    questNames.add(s);

                }

                Collections.sort(questNames, new Comparator<String>() {
                    @Override
                    public int compare(String one, String two) {

                        return one.compareTo(two);

                    }
                });

                if (isRequiredQuest) {
                    context.setSessionData(CK.REQ_QUEST, questNames);
                } else {
                    context.setSessionData(CK.REQ_QUEST_BLOCK, questNames);
                }

            } else if (input.equalsIgnoreCase("clear")) {

                if (isRequiredQuest) {
                    context.setSessionData(CK.REQ_QUEST, null);
                } else {
                    context.setSessionData(CK.REQ_QUEST_BLOCK, null);
                }

            }

            return new RequirementsPrompt(quests, factory);

        }
    }

    private class ItemListPrompt extends FixedSetPrompt {

        public ItemListPrompt() {

            super("1", "2", "3", "4");

        }

        @Override
        public String getPromptText(ConversationContext context) {

            // Check/add newly made item
            if (context.getSessionData("newItem") != null) {
                if (context.getSessionData(CK.REQ_ITEMS) != null) {
                    List<ItemStack> itemRews = getItems(context);
                    itemRews.add((ItemStack) context.getSessionData("tempStack"));
                    context.setSessionData(CK.REQ_ITEMS, itemRews);
                } else {
                    LinkedList<ItemStack> itemRews = new LinkedList<ItemStack>();
                    itemRews.add((ItemStack) context.getSessionData("tempStack"));
                    context.setSessionData(CK.REQ_ITEMS, itemRews);
                }

                context.setSessionData("newItem", null);
                context.setSessionData("tempStack", null);

            }

            String text = GOLD + "- Item Requirements -\n";
            if (context.getSessionData(CK.REQ_ITEMS) == null) {
                text += BLUE + "" + BOLD + "1" + RESET + YELLOW + " - Add item\n";
                text += GRAY + "2 - Set remove items (No items set)\n";
                text += BLUE + "" + BOLD + "3" + RESET + YELLOW + " - Clear\n";
                text += BLUE + "" + BOLD + "4" + RESET + YELLOW + " - Done";
            } else {

                for (ItemStack is : getItems(context)) {

                    text += GRAY + "    - " + ItemUtil.getDisplayString(is) + "\n";

                }

                text += BLUE + "" + BOLD + "1" + RESET + YELLOW + " - Add item\n";

                if (context.getSessionData(CK.REQ_ITEMS_REMOVE) == null) {
                    text += BLUE + "" + BOLD + "2" + RESET + YELLOW + " - Set remove items (No values set)\n";
                } else {

                    text += BLUE + "" + BOLD + "2" + RESET + YELLOW + " - Set remove items\n";
                    for (Boolean b : getRemoveItems(context)) {

                        text += GRAY + "    - " + AQUA + b.toString().toLowerCase() + "\n";

                    }

                }

                text += BLUE + "" + BOLD + "3" + RESET + YELLOW + " - Clear\n";
                text += BLUE + "" + BOLD + "4" + RESET + YELLOW + " - Done";

            }

            return text;

        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("1")) {
                return new ItemStackPrompt(ItemListPrompt.this);
            } else if (input.equalsIgnoreCase("2")) {
                if (context.getSessionData(CK.REQ_ITEMS) == null) {
                    context.getForWhom().sendRawMessage(RED + "You must add at least one item first!");
                    return new ItemListPrompt();
                } else {
                    return new RemoveItemsPrompt();
                }
            } else if (input.equalsIgnoreCase("3")) {
                context.getForWhom().sendRawMessage(YELLOW + "Item requirements cleared.");
                context.setSessionData(CK.REQ_ITEMS, null);
                context.setSessionData(CK.REQ_ITEMS_REMOVE, null);
                return new ItemListPrompt();
            } else if (input.equalsIgnoreCase("4")) {

                int one;
                int two;

                if (context.getSessionData(CK.REQ_ITEMS) != null) {
                    one = ((List<ItemStack>) context.getSessionData(CK.REQ_ITEMS)).size();
                } else {
                    one = 0;
                }

                if (context.getSessionData(CK.REQ_ITEMS_REMOVE) != null) {
                    two = ((List<Boolean>) context.getSessionData(CK.REQ_ITEMS_REMOVE)).size();
                } else {
                    two = 0;
                }

                if (one == two) {
                    return new RequirementsPrompt(quests, factory);
                } else {
                    context.getForWhom().sendRawMessage(RED + "The " + GOLD + "items list " + RED + "and " + GOLD + "remove items list " + RED + "are not the same size!");
                    return new ItemListPrompt();
                }
            }
            return null;

        }

        private List<ItemStack> getItems(ConversationContext context) {
            return (List<ItemStack>) context.getSessionData(CK.REQ_ITEMS);
        }

        private List<Boolean> getRemoveItems(ConversationContext context) {
            return (List<Boolean>) context.getSessionData(CK.REQ_ITEMS_REMOVE);
        }
    }

    private class RemoveItemsPrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            return YELLOW + "Enter a list of true/false values, separating each one by a space, or enter \'cancel\' to return.";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("cancel") == false) {

                String[] args = input.split(" ");
                LinkedList<Boolean> booleans = new LinkedList<Boolean>();

                for (String s : args) {

                    if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes")) {
                        booleans.add(true);
                    } else if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("no")) {
                        booleans.add(false);
                    } else {
                        context.getForWhom().sendRawMessage(PINK + s + RED + " is not a true or false value!\n " + GOLD + "Example: true false true true");
                        return new RemoveItemsPrompt();
                    }

                }

                context.setSessionData(CK.REQ_ITEMS_REMOVE, booleans);

            }

            return new ItemListPrompt();

        }
    }

    private class PermissionsPrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            return YELLOW + "Enter permission requirements separating each one by a space, or enter \'clear\' to clear the list, or enter \'cancel\' to return.";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("cancel") == false && input.equalsIgnoreCase("clear") == false) {

                String[] args = input.split(" ");
                LinkedList<String> permissions = new LinkedList<String>();
                permissions.addAll(Arrays.asList(args));

                context.setSessionData(CK.REQ_PERMISSION, permissions);

            } else if (input.equalsIgnoreCase("clear")) {
                context.setSessionData(CK.REQ_PERMISSION, null);
            }

            return new RequirementsPrompt(quests, factory);

        }
    }
    
    private class CustomRequirementsPrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            String text = PINK + "- Custom Requirements -\n";
            if(quests.customRequirements.isEmpty()){
                text += BOLD + "" + PURPLE + "(No modules loaded)";
            }else {
                for(CustomRequirement cr : quests.customRequirements)
                    text += PURPLE + " - " + cr.getName() + "\n";
            }
            
            return text + YELLOW + "Enter the name of a custom requirement to add, or enter \'clear\' to clear all custom requirements, or \'cancel\' to return.";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("cancel") == false && input.equalsIgnoreCase("clear") == false) {

                CustomRequirement found = null;
                for(CustomRequirement cr : quests.customRequirements){
                    if(cr.getName().equalsIgnoreCase(input)){
                        found = cr;
                        break;
                    }
                }
                
                if(found == null){
                    for(CustomRequirement cr : quests.customRequirements){
                        if(cr.getName().toLowerCase().contains(input.toLowerCase())){
                            found = cr;
                            break;
                        }
                    }
                }
                
                if(found != null){
                    
                    if(context.getSessionData(CK.REQ_CUSTOM) != null){
                        LinkedList<String> list = (LinkedList<String>) context.getSessionData(CK.REQ_CUSTOM);
                        LinkedList<Map<String, Object>> datamapList = (LinkedList<Map<String, Object>>) context.getSessionData(CK.REQ_CUSTOM_DATA);
                        if(list.contains(found.getName()) == false){
                            list.add(found.getName());
                            datamapList.add(found.datamap);
                            context.setSessionData(CK.REQ_CUSTOM, list);
                            context.setSessionData(CK.REQ_CUSTOM_DATA, datamapList);
                        }else{
                            context.getForWhom().sendRawMessage(YELLOW + "That custom requirement has already been added!");
                            return new CustomRequirementsPrompt();
                        }
                    }else{
                        LinkedList<Map<String, Object>> datamapList = new LinkedList<Map<String, Object>>();
                        datamapList.add(found.datamap);
                        LinkedList<String> list = new LinkedList<String>();
                        list.add(found.getName());
                        context.setSessionData(CK.REQ_CUSTOM, list);
                        context.setSessionData(CK.REQ_CUSTOM_DATA, datamapList);
                    }
                    
                    //Send user to the custom data prompt if there is any needed
                    if(found.datamap.isEmpty() == false){
                        
                        context.setSessionData(CK.REQ_CUSTOM_DATA_DESCRIPTIONS, found.descriptions);
                        return new RequirementCustomDataListPrompt();
                        
                    }
                    //
                    
                }else{
                    context.getForWhom().sendRawMessage(YELLOW + "Custom requirement module not found.");
                    return new CustomRequirementsPrompt();
                }

            } else if (input.equalsIgnoreCase("clear")) {
                context.setSessionData(CK.REQ_CUSTOM, null);
                context.setSessionData(CK.REQ_CUSTOM_DATA, null);
                context.setSessionData(CK.REQ_CUSTOM_DATA_TEMP, null);
                context.getForWhom().sendRawMessage(YELLOW + "Custom requirements cleared.");
            }

            return new RequirementsPrompt(quests, factory);

        }
    }
    
    private class RequirementCustomDataListPrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            
            String text = BOLD + "" + AQUA + "- ";
            
            LinkedList<String> list = (LinkedList<String>) context.getSessionData(CK.REQ_CUSTOM);
            LinkedList<Map<String, Object>> datamapList = (LinkedList<Map<String, Object>>) context.getSessionData(CK.REQ_CUSTOM_DATA);
            
            String reqName = list.getLast();
            Map<String, Object> datamap = datamapList.getLast();
            
            text += reqName + " -\n";
            int index = 1;
            
            LinkedList<String> datamapKeys = new LinkedList<String>();
            for(String key : datamap.keySet())
                datamapKeys.add(key);
            Collections.sort(datamapKeys);
            
            for(String dataKey : datamapKeys){
                
                text += BOLD + "" + DARKBLUE + index + " - " + RESET + BLUE + dataKey;
                if(datamap.get(dataKey) != null)
                    text += GREEN + " (" + (String) datamap.get(dataKey) + ")\n";
                else
                    text += RED + " (Value required)\n";
                
                index++;
                
            }
            
            text += BOLD + "" + DARKBLUE + index + " - " + AQUA + "Finish";
            
            return text;
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            
            LinkedList<Map<String, Object>> datamapList = (LinkedList<Map<String, Object>>) context.getSessionData(CK.REQ_CUSTOM_DATA);
            Map<String, Object> datamap = datamapList.getLast();
            
            int numInput;
            
            try{
                numInput = Integer.parseInt(input);
            }catch(NumberFormatException nfe){
                return new RequirementCustomDataListPrompt();
            }
            
            if(numInput < 1 || numInput > datamap.size() + 1)
                return new RequirementCustomDataListPrompt();
            
            if(numInput < datamap.size() + 1){
                
                LinkedList<String> datamapKeys = new LinkedList<String>();
                for(String key : datamap.keySet())
                    datamapKeys.add(key);
                Collections.sort(datamapKeys);

                String selectedKey = datamapKeys.get(numInput - 1);
                context.setSessionData(CK.REQ_CUSTOM_DATA_TEMP, selectedKey);
                return new RequirementCustomDataPrompt();
                
            }else{
                
                if(datamap.containsValue(null)){
                    return new RequirementCustomDataListPrompt();
                }else{
                    context.setSessionData(CK.REQ_CUSTOM_DATA_DESCRIPTIONS, null);
                    return new RequirementsPrompt(quests, factory);
                }
                
            }

        }
        
    }
    
    private class RequirementCustomDataPrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            String text = "";
            String temp = (String)context.getSessionData(CK.REQ_CUSTOM_DATA_TEMP);
            Map<String, String> descriptions = (Map<String, String>) context.getSessionData(CK.REQ_CUSTOM_DATA_DESCRIPTIONS);
            if(descriptions.get(temp) != null)
                text += GOLD + descriptions.get(temp) + "\n";
                
            text += YELLOW + "Enter value for ";
            text += BOLD + temp + RESET + YELLOW + ":";
            return text;
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            LinkedList<Map<String, Object>> datamapList = (LinkedList<Map<String, Object>>) context.getSessionData(CK.REQ_CUSTOM_DATA);
            Map<String, Object> datamap = datamapList.getLast();
            datamap.put((String)context.getSessionData(CK.REQ_CUSTOM_DATA_TEMP), input);
            context.setSessionData(CK.REQ_CUSTOM_DATA_TEMP, null);
            return new RequirementCustomDataListPrompt();
        }
        
    }

    private class FailMessagePrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            return YELLOW + "Enter fail requirements message, or enter \'cancel\' to return.";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            if (input.equalsIgnoreCase("cancel") == false) {
                context.setSessionData(CK.Q_FAIL_MESSAGE, input);
            }

            return new RequirementsPrompt(quests, factory);

        }
    }
}
