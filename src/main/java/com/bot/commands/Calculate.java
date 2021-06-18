package com.bot.commands;

import com.bot.commands.UserRelated.Mute;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class Calculate extends ListenerAdapter {
    Map<String, BinaryOperator<Integer>> operatorMap;

    private static Calculate single_instance = null;


    public static Calculate getInstance() {
        if (single_instance == null)
            single_instance = new Calculate();

        return single_instance;
    }
    private Calculate() {
        operatorMap = Map.ofEntries(
                Map.entry("+", Integer::sum),
                Map.entry("-", (a, b) -> a - b),
                Map.entry("*", (a, b) -> a * b),
                Map.entry("/", (a, b) -> a / b),
                Map.entry("%", (a, b) -> a % b)
        );
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> command = List.of(event.getMessage().getContentRaw().split(" +"));
        if (command.get(0).equalsIgnoreCase("!calc")) {
            try {
                Integer answer = operatorMap.get(command.get(2)).apply(Integer.parseInt(command.get(1)), Integer.parseInt(command.get(3)));
                event.getChannel().sendMessage(answer.toString()).queue();
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().sendMessage("Comanda invalida. Sintaxa este: \"!calc <operand1> <operator> <operand2>\" ").queue();
            }
        }
    }

    public static String getHelpCommand() {
        return "!help calc";
    }

    public static String getHelpSyntax() {
        return "Sintaxa: !calc <operand1> <operator> <operand2> ";
    }

    public static String getHelpUsage() {
        return "Efectueaza operatii banale cu numere intregi.";
    }
}
