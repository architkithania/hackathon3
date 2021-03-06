package EmployerSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Employer {
    private static JLabel jobTitleLabel;
    private static JComboBox jobTitleComboBox;
    private static JLabel slotsLabel;
    private static JTextField slotsFields;
    private static JLabel priority1Label;
    private static JComboBox priority1ComboBox;
    private static JLabel priority2Label;
    private static JComboBox priority2ComboBox;
    private static JLabel priority3Label;
    private static JComboBox priority3ComboBox;
    private static JButton button;

    private static ArrayList<String> skills;

    private static String[] priorityOptions = {"", "Education", "Experience", "Skills"};

    private static JList applicants;

    public static ArrayList<String> getListOfSkills(){
        ArrayList<String> skillsToReturn = new ArrayList<String>();
        try {
            ParseAPI parseAPI = new ParseAPI();
            for(int i = 0; i < parseAPI.records.length; i++){
                    String[] s = parseAPI.records[i].skills.split(",");
                for(int j = 0; j < s.length; j++){
                    if(!skillsToReturn.contains(s[j])){
                        skillsToReturn.add(s[j]);
                    }
                }
            }
            return skillsToReturn;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static ArrayList<String> getListOfJobTitles(){
        ArrayList<String> jobTitlesToReturn = new ArrayList<String>();
        try {
            ParseAPI parseAPI = new ParseAPI();
            for(int i = 0; i < parseAPI.records.length; i++){
                jobTitlesToReturn.add(parseAPI.records[i].jobtitle);
            }
            return jobTitlesToReturn;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(600, 800);
        GridBagConstraints gc = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel applicantsPanel = new JPanel();
        frame.add(panel);

        Insets labelInsets = new Insets(0, 0, 0, 5);
        Insets blankInsets = new Insets(0,0,0,0);

        gc.weightx = 1.0;
        gc.weighty = 1.0;

        jobTitleLabel = new JLabel("Job Title:");
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        panel.add(jobTitleLabel, gc);

        jobTitleComboBox = new JComboBox(getListOfJobTitles().toArray());
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = blankInsets;
        panel.add(jobTitleComboBox, gc);

        slotsLabel = new JLabel("Interview Slots:");
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        panel.add(slotsLabel, gc);

        slotsFields = new JTextField(10);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = labelInsets;
        panel.add(slotsFields, gc);

        priority1Label = new JLabel("1st Priority:");
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        panel.add(priority1Label, gc);

        priority1ComboBox = new JComboBox<>(priorityOptions);
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = blankInsets;
        panel.add(priority1ComboBox, gc);

        priority2Label = new JLabel("2nd Priority:");
        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        panel.add(priority2Label, gc);

        priority2ComboBox = new JComboBox<>(priorityOptions);
        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = blankInsets;
        panel.add(priority2ComboBox, gc);

        priority3Label = new JLabel("1st Priority:");
        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = labelInsets;
        panel.add(priority3Label, gc);

        priority3ComboBox = new JComboBox<>(priorityOptions);
        gc.gridx = 1;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = blankInsets;
        panel.add(priority3ComboBox, gc);

        button = new JButton("Done");
        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = blankInsets;
        panel.add(button, gc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(slotsFields.getText());
                String [] priorityList = new String [3];
                priorityList[0] = (String) priority1ComboBox.getSelectedItem();
                priorityList[1] = (String) priority2ComboBox.getSelectedItem();
                priorityList[2] = (String) priority3ComboBox.getSelectedItem();

                String jobTitle = (String) jobTitleComboBox.getSelectedItem();

                SortedCandidates candidates = new SortedCandidates(value, priorityList, jobTitle);

                String [] parser = new String[candidates.ids.length];

                for (int i = 0; i < candidates.ids.length; i++) {
                    parser[i] = Integer.toString(candidates.ids[i]);
                }

                frame.remove(panel);
                frame.setVisible(true);
                frame.add(applicantsPanel);
                applicants = new JList<>(parser);
                applicants.setFont(new Font("Arial", Font.PLAIN, 36));
                applicantsPanel.add(applicants);
                frame.revalidate();
            }
        });

        priority1ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                priority2ComboBox.removeAllItems();
                priority3ComboBox.removeAllItems();
                for(int i = 0; i < priorityOptions.length; i++){
                    priority2ComboBox.addItem(priorityOptions[i]);
                }
                priority2ComboBox.removeItem(priority1ComboBox.getSelectedItem());
                priority3ComboBox.removeItem(priority1ComboBox.getSelectedItem());
            }
        });

        priority2ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                priority3ComboBox.removeAllItems();
                for(int i = 0; i < priorityOptions.length; i++){
                    priority3ComboBox.addItem(priorityOptions[i]);
                }
                priority3ComboBox.removeItem(priority1ComboBox.getSelectedItem());
                priority3ComboBox.removeItem(priority2ComboBox.getSelectedItem());
            }
        });

        frame.setVisible(true);
    }
}