package com.ekakashi.greenhouse.database.model_server_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCondition;
import com.ekakashi.greenhouse.database.model_server_response.ObjectEkField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectEkFields;
import com.ekakashi.greenhouse.database.model_server_response.ObjectEkUser;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.database.model_server_response.RevisionHistory;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptloc on 1/16/2018.
 */

public class EditRecipeRequest {
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("publicFlag")
    public int publicFlag;
    @SerializedName("publicContent4")
    public boolean publicContent4;
    @SerializedName("publicContent3")
    public boolean publicContent3;
    @SerializedName("publicContent2")
    public boolean publicContent2;
    @SerializedName("publicContent1")
    public boolean publicContent1;
    @SerializedName("parentRecipeId")
    public int parentRecipeId;
    @SerializedName("officialFlag")
    public int officialFlag;
    @SerializedName("imageType")
    public int imageType;
    @SerializedName("description")
    public String description;
    @SerializedName("createdFlag")
    public int createdFlag;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("contestFlag")
    public int contestFlag;
    @SerializedName("changeAvailability")
    public boolean changeAvailability;
    @SerializedName("category")
    public String category;
    @SerializedName("crop")
    public String crop;
    @SerializedName("ekfields")
    public List<Ekfields> ekfields;
    @SerializedName("ekUser")
    public EkUser ekUser;
    @SerializedName("id")
    public int id;
    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("recipeName")
    public String recipeName;
    @SerializedName("recipeType")
    public boolean recipeType;
    @SerializedName("prefectures")
    public String prefectures;
    @SerializedName("scope")
    public boolean scope;
    @SerializedName("recipeStages")
    public List<RecipeStages> recipeStages;
    @SerializedName("recipeVersion")
    public int recipeVersion;
    @SerializedName("revisionHistory")
    public List<History> revisionHistory;
    @SerializedName("organizationName")
    public String organizationName;
    @SerializedName("templates")
    public List<Template> templates;
    @SerializedName("iscomplete")
    public boolean complete;

    public void parseToRequestObject(ObjectRecipe recipe) {
        if (recipe.getDescription() == null || recipe.getDescription().isEmpty()) {
            this.description = "";
        } else {
            this.description = recipe.getDescription();
        }

        this.changeAvailability = recipe.isAvailability();
        if (recipe.getCrop() == null || recipe.getCrop().isEmpty()) {
            this.crop = "";
        } else {
            this.crop = recipe.getCrop();
        }
        if (recipe.getCategory() == null || recipe.getCategory().isEmpty()) {
            this.category = "";
        } else {
            this.category = recipe.getCategory();
        }
        this.ekfields = parseToEkFields(recipe.getEkFields());
        if (recipe.getEkUser() != null) {
            this.ekUser = new EkUser(recipe.getEkUser());
        } else {
            this.ekUser = null;
        }
        this.id = recipe.getId();
        if (recipe.getImage() == null || recipe.getImage().isEmpty()) {
            this.imagePath = "";
        } else {
            this.imagePath = recipe.getImage();
        }
        if (recipe.getRecipeName() == null || recipe.getRecipeName().isEmpty()) {
            this.recipeName = "";
        } else {
            this.recipeName = recipe.getRecipeName();
        }
        this.recipeType = recipe.isRecipeType();
        if (recipe.getPrefecture() == null || recipe.getPrefecture().isEmpty()) {
            this.prefectures = "";
        } else {
            this.prefectures = recipe.getPrefecture();
        }

        this.scope = recipe.isScope();

        this.recipeStages = parseToStageList(recipe.getStages());
        this.recipeVersion = recipe.getVersion();
        this.revisionHistory = parseToRevisionHistoryList(recipe.getRevisionHistoryList());
        if (recipe.getOrganizationName() == null || recipe.getOrganizationName().isEmpty()) {
            this.organizationName = "";
        } else {
            this.organizationName = recipe.getOrganizationName();
        }
        this.parentRecipeId = recipe.getParentRecipeId();
        this.imageType = recipe.getImageType();
        this.officialFlag = recipe.getOfficialFlag();
        this.publicFlag = recipe.getPublicFlag();
        this.publicContent1 = recipe.isPublicContent1();
        this.publicContent2 = recipe.isPublicContent2();
        this.publicContent3 = recipe.isPublicContent3();
        this.publicContent4 = recipe.isPublicContent4();
        this.templates = recipe.getTemplates();
        this.complete = recipe.isComplete();
    }

    public static class Template implements Parcelable {
        @SerializedName("isDefault")
        public boolean isDefault;
        @SerializedName("numberOfColumn")
        public int numberOfColumn;
        @SerializedName("templateType")
        public int templateType;
        @SerializedName("iconUrl")
        public String iconUrl;
        @SerializedName("graph")
        public Graph graph;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public int id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
            dest.writeInt(this.numberOfColumn);
            dest.writeInt(this.templateType);
            dest.writeString(this.iconUrl);
            dest.writeParcelable(this.graph, flags);
            dest.writeString(this.name);
            dest.writeInt(this.id);
        }

        public Template() {
        }

        protected Template(Parcel in) {
            this.isDefault = in.readByte() != 0;
            this.numberOfColumn = in.readInt();
            this.templateType = in.readInt();
            this.iconUrl = in.readString();
            this.graph = in.readParcelable(Graph.class.getClassLoader());
            this.name = in.readString();
            this.id = in.readInt();
        }

        public static final Creator<Template> CREATOR = new Creator<Template>() {
            @Override
            public Template createFromParcel(Parcel source) {
                return new Template(source);
            }

            @Override
            public Template[] newArray(int size) {
                return new Template[size];
            }
        };
    }

    public static class Graph implements Parcelable {
        @SerializedName("numberOfColumn")
        public int numberOfColumn;
        @SerializedName("icon")
        public String icon;
        @SerializedName("name")
        public String name;
        @SerializedName("tags")
        public String tags;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("isXAxisIntegration")
        public boolean isXAxisIntegration;
        @SerializedName("isXAxisMovingAverage")
        public boolean isXAxisMovingAverage;
        @SerializedName("xAxisAggregateType")
        public int xAxisAggregateType;
        @SerializedName("isXAxisDatetime")
        public boolean isXAxisDatetime;
        @SerializedName("sortNo")
        public int sortNo;
        @SerializedName("isDisplayStage")
        public boolean isDisplayStage;
        @SerializedName("temType")
        public int temType;
        @SerializedName("xGraphMeasurementItemId")
        public int xGraphMeasurementItemId;
        @SerializedName("id")
        public int id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.numberOfColumn);
            dest.writeString(this.icon);
            dest.writeString(this.name);
            dest.writeString(this.tags);
            dest.writeString(this.updatedAt);
            dest.writeString(this.createdAt);
            dest.writeByte(this.isXAxisIntegration ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isXAxisMovingAverage ? (byte) 1 : (byte) 0);
            dest.writeInt(this.xAxisAggregateType);
            dest.writeByte(this.isXAxisDatetime ? (byte) 1 : (byte) 0);
            dest.writeInt(this.sortNo);
            dest.writeByte(this.isDisplayStage ? (byte) 1 : (byte) 0);
            dest.writeInt(this.temType);
            dest.writeInt(this.xGraphMeasurementItemId);
            dest.writeInt(this.id);
        }

        public Graph() {
        }

        protected Graph(Parcel in) {
            this.numberOfColumn = in.readInt();
            this.icon = in.readString();
            this.name = in.readString();
            this.tags = in.readString();
            this.updatedAt = in.readString();
            this.createdAt = in.readString();
            this.isXAxisIntegration = in.readByte() != 0;
            this.isXAxisMovingAverage = in.readByte() != 0;
            this.xAxisAggregateType = in.readInt();
            this.isXAxisDatetime = in.readByte() != 0;
            this.sortNo = in.readInt();
            this.isDisplayStage = in.readByte() != 0;
            this.temType = in.readInt();
            this.xGraphMeasurementItemId = in.readInt();
            this.id = in.readInt();
        }

        public static final Creator<Graph> CREATOR = new Creator<Graph>() {
            @Override
            public Graph createFromParcel(Parcel source) {
                return new Graph(source);
            }

            @Override
            public Graph[] newArray(int size) {
                return new Graph[size];
            }
        };
    }

    public static class Categories {
        @SerializedName("image")
        public String image;
        @SerializedName("categoryName")
        public String categoryName;
        @SerializedName("id")
        public int id;

        public Categories(ObjectCategory category) {
            this.image = category.getImage();
            this.id = category.getId();
            this.categoryName = category.getName();
        }
    }

    public static class History {
        @SerializedName("recipeVersion")
        public int recipeVersion;
        @SerializedName("recipeStages")
        public List<RecipeStages> recipeStages;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("recipeName")
        public String recipeName;
        @SerializedName("publicFlag")
        public int publicFlag;
        @SerializedName("prefectures")
        public String prefectures;
        @SerializedName("category")
        public String category;
        @SerializedName("ekfields")
        public List<Ekfields> ekfields;
        @SerializedName("crop")
        public String crop;
        @SerializedName("recipeType")
        public boolean recipeType;
        @SerializedName("changeAvailability")
        public boolean changeAvailability;
        @SerializedName("scope")
        public boolean scope;
        @SerializedName("organizationName")
        public String organizationName;
        @SerializedName("officialFlag")
        public int officialFlag;
        @SerializedName("imageType")
        public int imageType;
        @SerializedName("imagePath")
        public String imagePath;
        @SerializedName("ekUser")
        public EkUser ekUser;
        @SerializedName("description")
        public String description;
        @SerializedName("createdFlag")
        public int createdFlag;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("contestFlag")
        public int contestFlag;
        @SerializedName("id")
        public int id;
        @SerializedName("iscomplete")
        public boolean complete;
        @SerializedName("templates")
        public List<Template> templates;

        History(RevisionHistory revisionHistory) {
            this.id = revisionHistory.getId();
            this.contestFlag = revisionHistory.getContestFlag();
            this.createdFlag = revisionHistory.getCreatedFlag();
            if (revisionHistory.getDescription() == null || revisionHistory.getDescription().isEmpty()) {
                this.description = "";
            } else {
                this.description = revisionHistory.getDescription();
            }
            if (revisionHistory.getEkUser() != null) {
                this.ekUser = new EkUser(revisionHistory.getEkUser());
            } else {
                this.ekUser = null;
            }

            this.imageType = revisionHistory.getImageType();
            this.officialFlag = revisionHistory.getOfficialFlag();
            if (revisionHistory.getOrganizationName() == null || revisionHistory.getOrganizationName().isEmpty()) {
                this.organizationName = "";
            } else {
                this.organizationName = revisionHistory.getOrganizationName();
            }
            this.scope = revisionHistory.isScope();
            this.changeAvailability = revisionHistory.isChangeAvailability();
            this.recipeType = revisionHistory.isRecipeType();
            this.ekfields = parseToEkFields(revisionHistory.getEkfields());
            if (revisionHistory.getCategory() == null || revisionHistory.getCategory().isEmpty()) {
                this.category = "";
            } else {
                this.category = revisionHistory.getCategory();
            }
            if (revisionHistory.getPrefectures() == null || revisionHistory.getPrefectures().isEmpty()) {
                this.prefectures = "";
            } else {
                this.prefectures = revisionHistory.getPrefectures();
            }
            if (revisionHistory.getRecipeName() == null || revisionHistory.getRecipeName().isEmpty()) {
                this.recipeName = "";
            } else {
                this.recipeName = revisionHistory.getRecipeName();
            }
            this.recipeVersion = revisionHistory.getRecipeVersion();
            this.recipeStages = parseToStageList(revisionHistory.getRecipeStages());
            if (revisionHistory.getImagePath() == null || revisionHistory.getImagePath().isEmpty()) {
                this.imagePath = "";
            } else {
                this.imagePath = revisionHistory.getImagePath();
            }
            this.publicFlag = revisionHistory.getPublicFlag();
            this.complete = revisionHistory.isComplete();
            this.templates = revisionHistory.getTemplates();
        }
    }

    public static class CurrentStage {
        @SerializedName("id")
        public int id;

        CurrentStage(ObjectGrowth currentStage) {
            this.id = currentStage.getId();
        }
    }

    public static class Ekfield {
        @SerializedName("id")
        public int id;
        @SerializedName("polygon")
        private List<ObjectEkField.Polygon> polygon;
        @SerializedName("fieldType")
        private int fieldType;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("startDate")
        private String startDate;
        @SerializedName("location")
        private String location;
        @SerializedName("name")
        private String name;
        @SerializedName("ekUser")
        private ObjectEkUser ekUser;
        @SerializedName("createdAt")
        private String createdAt;

        Ekfield(ObjectEkField ekField) {
            this.id = ekField.getId();
            if (ekField.getName() == null || ekField.getName().isEmpty()) {
                this.name = "";
            } else {
                this.name = ekField.getName();
            }
            this.ekUser = ekField.getEkUser();
            this.location = ekField.getLocation();
            this.fieldType = ekField.getFieldType();
            this.polygon = ekField.getPolygon();
        }
    }

    public static class Ekfields {
        @SerializedName("currentStage")
        public CurrentStage currentStage;
        @SerializedName("ekfield")
        public Ekfield ekfield;
        @SerializedName("id")
        public int id;

        public Ekfields(ObjectEkFields ekFields) {
            this.ekfield = new Ekfield(ekFields.getEkField());
            this.id = ekFields.getId();
            this.currentStage = new CurrentStage(ekFields.getCurrentStage());
        }
    }

    public static class EkUser {
        @SerializedName("validEmail")
        public boolean validEmail;
        @SerializedName("userName")
        public String userName;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("salt")
        public String salt;
        @SerializedName("password")
        public String password;
        @SerializedName("officialUserFlag")
        public int officialUserFlag;
        @SerializedName("nickName")
        public String nickName;
        @SerializedName("name")
        public String name;
        @SerializedName("lastLoginDatetime")
        public String lastLoginDatetime;
        @SerializedName("isActive")
        public boolean isActive;
        @SerializedName("email")
        public String email;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("id")
        public int id;

        public EkUser(ObjectEkUser ekUser) {
            if (ekUser.getEmail() == null || ekUser.getEmail().isEmpty()) {
                this.email = "";
            } else {
                this.email = ekUser.getEmail();
            }

            this.id = ekUser.getId();

            this.isActive = ekUser.isActive();

            if (ekUser.getName() == null || ekUser.getName().isEmpty()) {
                this.name = "";
            } else {
                this.name = ekUser.getName();
            }

            if (ekUser.getNickName() == null || ekUser.getNickName().isEmpty()) {
                this.nickName = "";
            } else {
                this.nickName = ekUser.getNickName();
            }

            if (ekUser.getUserName() == null || ekUser.getUserName().isEmpty()) {
                this.userName = "";
            } else {
                this.userName = ekUser.getUserName();
            }
        }
    }

    public static class RecipeStages {
        @SerializedName("rules")
        public List<Rules> rules;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("stageDescription")
        public String stageDescription;
        @SerializedName("stageName")
        public String stageName;
        @SerializedName("sortNo")
        public int sortNo;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("id")
        public Integer id;

        public RecipeStages(ObjectGrowth stage) {
            if (stage.getDescription() == null || stage.getDescription().isEmpty()) {
                this.stageDescription = "";
            } else {
                this.stageDescription = stage.getDescription();
            }

            this.id = stage.getId();
            if (stage.getName() == null || stage.getName().isEmpty()) {
                this.stageName = "";
            } else {
                this.stageName = stage.getName();
            }
            this.sortNo = stage.getOrderPos();
            this.rules = parseToRuleList(stage.getRules());

        }
    }

    public static class Rules {
        @SerializedName("isNotify")
        public boolean isNotify;
        @SerializedName("ruleType")
        public int ruleType;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("actions")
        public List<Actions> actions;
        @SerializedName("conditions")
        public List<Conditions> conditions;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public Integer id;

        Rules(ObjectRule rule) {
            this.id = rule.getId();
            if (rule.getName() == null || rule.getName().isEmpty()) {
                this.name = "";
            } else {
                this.name = rule.getName();
            }
            this.ruleType = rule.getRuleType();
            this.actions = parseToActionList(rule.getActions());
            this.conditions = parseToConditionList(rule.getConditions());
            this.isNotify = rule.isNotify();
        }

    }

    public static class Actions {
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("url")
        public String url;
        @SerializedName("image")
        public String image;
        @SerializedName("name")
        public String name;
        @SerializedName("content")
        public String content;
        @SerializedName("id")
        public Integer id;
        @SerializedName("title")
        private String title;
        @SerializedName("sortNo")
        public int sortNo;
        @SerializedName("actionType")
        public int actionType;

        Actions(ObjectAction action) {
            this.id = action.getId();
            if (action.getName() == null || action.getName().isEmpty()) {
                this.name = "";
            } else {
                this.name = action.getName();
            }

            if (action.getImage() == null || action.getImage().isEmpty()) {
                this.image = "";
            } else {
                this.image = action.getImage();
            }

            if (action.getUrl() == null || action.getUrl().isEmpty()) {
                this.url = "";
            } else {
                this.url = action.getUrl();
            }

            if (action.getContent() == null || action.getContent().isEmpty()) {
                this.content = "";
            } else {
                this.content = action.getContent();
            }

            if (action.getTitle() == null || action.getTitle().isEmpty()) {
                this.title = "";
            } else {
                this.title = action.getTitle();
            }

            this.sortNo = action.getSortNo();
            this.actionType = action.getActionType();
        }
    }

    public static class Conditions {
        @SerializedName("logicalOperator")
        private boolean logicalOperator;
        @SerializedName("sortNo")
        public int sortNo;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("determinationTarget")
        public Integer determinationTarget;
        @SerializedName("determinationMethod")
        public Integer determinationMethod;
        @SerializedName("determinationValue")
        public int determinationValue;
        @SerializedName("countingMethod")
        public Integer countingMethod;
        @SerializedName("deviceValue")
        public int deviceValue;
        @SerializedName("deviceType")
        public int deviceType;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public Integer id;
        @SerializedName("stage")
        public RecipeStages stage;

        Conditions(ObjectCondition condition) {
            this.id = condition.getId();
            if (condition.getName() == null || condition.getName().isEmpty()) {
                this.name = "";
            } else {
                this.name = condition.getName();
            }

            this.deviceType = condition.getDeviceType();
            this.deviceValue = condition.getMeasurementItem();
            this.countingMethod = condition.getCountingMethod();
            this.determinationValue = condition.getDeterminationValue();
            this.determinationMethod = condition.getDeterminationStandard();
            this.determinationTarget = condition.getAdditionalInformation();
            this.sortNo = condition.getSortNo();
            this.logicalOperator = condition.isLogicalOperator();
            if (condition.getStage() != null) {
                this.stage = new RecipeStages(condition.getStage());
            } else {
                this.stage = new RecipeStages(new ObjectGrowth());
            }
        }
    }

    private static List<Ekfields> parseToEkFields(List<ObjectEkFields> fieldsList) {
        List<Ekfields> ekfields = new ArrayList<>();
        if (fieldsList != null && !fieldsList.isEmpty()) {
            for (ObjectEkFields fields : fieldsList) {
                if (fields != null) {
                    ekfields.add(new Ekfields(fields));
                }
            }
        }
        return ekfields;
    }

    private static List<RecipeStages> parseToStageList(List<ObjectGrowth> stages) {
        List<RecipeStages> recipeStages = new ArrayList<>();
        if (stages != null && !stages.isEmpty()) {
            for (ObjectGrowth stage : stages) {
                if (stage != null) {
                    recipeStages.add(new RecipeStages(stage));
                }
            }
        }
        return recipeStages;
    }

    private static List<History> parseToRevisionHistoryList(List<RevisionHistory> historyList) {
        List<History> histories = new ArrayList<>();
        if (historyList != null && !historyList.isEmpty()) {
            for (RevisionHistory revisionHistory : historyList) {
                if (revisionHistory != null) {
                    histories.add(new History(revisionHistory));
                }
            }
        }
        return histories;
    }

    private static List<Categories> parseToCategoryList(List<ObjectCategory> categories) {
        List<Categories> list = new ArrayList<>();
        if (categories != null && !categories.isEmpty()) {
            for (ObjectCategory category : categories) {
                if (category != null) {
                    list.add(new Categories(category));
                }
            }
        }
        return list;
    }

    private static List<Rules> parseToRuleList(List<ObjectRule> rules) {
        List<Rules> rulesList = new ArrayList<>();
        if (rules != null && !rules.isEmpty()) {
            for (ObjectRule rule : rules) {
                if (rule != null) {
                    rulesList.add(new Rules(rule));
                }
            }
        }
        return rulesList;
    }

    private static List<Actions> parseToActionList(List<ObjectAction> actions) {
        List<Actions> actionsList = new ArrayList<>();
        if (actions != null && !actions.isEmpty()) {
            for (ObjectAction action : actions) {
                if (action != null) {
                    actionsList.add(new Actions(action));
                }
            }
        }
        return actionsList;
    }

    private static List<Conditions> parseToConditionList(List<ObjectCondition> conditions) {
        List<Conditions> conditionsList = new ArrayList<>();
        if (conditions != null && !conditions.isEmpty()) {
            for (ObjectCondition condition : conditions) {
                if (condition != null) {
                    conditionsList.add(new Conditions(condition));
                }
            }
        }
        return conditionsList;
    }
}
