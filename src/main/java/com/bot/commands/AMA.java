package com.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AMA extends ListenerAdapter {
    private static AMA single_instance = null;
    final Map<String, String> qna;


    //TODO
    private AMA() {
        qna = Map.ofEntries(
                Map.entry("ma poti ajuta?", "Foloseste comanda <!help>"),
                Map.entry("stii java?", "La maxim."),
                Map.entry("A", "Din pacate nu pot sa raspund la aceasta intrebare."),
                Map.entry("unde pot invata java?", "Recomand https://profs.info.uaic.ro/~acf/java/ sau chiar https://www.w3schools.com/java/."),
                Map.entry("unde pot invata python?", "Cred ca https://www.w3schools.com/python/ te va ajuta."),
                Map.entry("unde pot invata c?", "Nu face asta!"),
                Map.entry("unde pot invata c++?", "Recomand site-ul domnului Patrut. jk poftim https://www.w3schools.com/cpp/"),
                Map.entry("imi poti recomanda niste carti?", "Sigur: https://github.com/tpn/pdfs. Ai aici peste o mie de carti care te pot ajuta sa devii un programator mai bun")

        );
    }

    public static AMA getInstance() {
        if (single_instance == null)
            single_instance = new AMA();

        return single_instance;
    }

    public static String getHelpCommand() {
        return "!help ask";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !ask <intrebare>";
    }

    public static String getHelpUsage() {
        return "Raspunde cu text predefinit la intrebari simple";
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
        String question;
        if (command.get(0).equalsIgnoreCase("!ask")) {
            if (command.size() > 1 && qna.containsKey(command.subList(1, command.size()).stream().reduce((a, b) -> a + " " + b).get().toLowerCase(Locale.ROOT)))
                question = qna.get(command.subList(1, command.size()).stream().reduce((a, b) -> a + " " + b).get().toLowerCase(Locale.ROOT));
            else question = qna.get("A");

            event.getChannel().sendMessage(question).queue();
        }
    }

}
