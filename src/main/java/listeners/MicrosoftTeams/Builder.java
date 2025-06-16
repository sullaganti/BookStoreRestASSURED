package listeners.MicrosoftTeams;

import java.util.Arrays;
import java.util.List;

import static listeners.MicrosoftTeams.AdaptiveCardReqBody.*;

public class Builder {

    List<FactsItem> FactsItems;

    public Builder(List<FactsItem> factsItems ) {
        FactsItems=factsItems;

    }

    public AdaptiveCardReqBody reqBody(){
        return builder()
                .type("message")
                .attachments(Arrays.asList(attachments()))
                .build();
    }

    public AttachmentsItem attachments(){

        return  AttachmentsItem.builder()
                .content(content())
                .contentType("application/vnd.microsoft.card.adaptive").build();

    }

    public Content content(){
        return Content.builder()
                .type("AdaptiveCard")
                .body(Arrays.asList(banner(),factSetBody(), buttons()))
                .build();
    }

    public BodyItem banner(){

        ItemsItem itemsItem1 = ItemsItem.builder()
                .type("TextBlock")
                .size("Large")
                .weight("Bolder")
                .text("**API Test Results**")
                .wrap(true)
                .style("heading")
                .build();

        ItemsItem itemsItem2 = ItemsItem.builder()
                .type("Image")
                .url("https://upload.wikimedia.org/wikipedia/en/thumb/e/ea/Kongsberg_Gruppen_logo.svg/1200px-Kongsberg_Gruppen_logo.svg.png")
                .altText("Failed")
                .height("50px")
                .build();

        ColumnsItem columnsItem1 = ColumnsItem.builder()
                .type("Column")
                .width("stretch")
                .items(Arrays.asList(itemsItem1)).build();

        ColumnsItem columnsItem2 = ColumnsItem.builder()
                .type("Column")
                .width("stretch")
                .items(Arrays.asList(itemsItem2)).build();

        ItemsItem itemsItem = ItemsItem.builder()
                .type("ColumnSet")
                .columns(Arrays.asList(columnsItem1,columnsItem2)).build();

       return  BodyItem.builder()
                .type("Container")
                .style("emphasis")
                .text("Test")
                .items(Arrays.asList(itemsItem))
                .build();


    }

    public BodyItem factSetBody(){

        return  BodyItem.builder()
                .type("FactSet")
                .spacing("Large")
                .facts(FactsItems)
                .build();
    }



    public BodyItem buttons(){
        ActionsItem actionsItem1 = ActionsItem.builder()
                .title("View Test Results")
                .type("Action.OpenUrl")
                .url("https://YOUR_REPORT_URL/")
                .style("destructive")
                .build();

        ActionsItem actionsItem2 = ActionsItem.builder()
                .title("Raise Bug")
                .type("Action.OpenUrl")
                .url("https://YOUR_BUG_TRACKER_URL/")
                .style("destructive")
                .build();

        ItemsItem itemsItem = ItemsItem.builder()
                .type("ActionSet")
                .actions(Arrays.asList(actionsItem1,actionsItem2)).build();

        return  BodyItem.builder()
                .type("Container")
                //.isVisible(false)
                .items(Arrays.asList(itemsItem))
                .build();
    }

}
