package com.men;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Story {

    private LinkedList<String> story = new LinkedList<>();

    public void addStoryMsg (String msg) {
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(msg);
        }
        else { story.add(msg); }
    }

    public void printStory (BufferedWriter writer) {
        if (story.size() > 0) {
            try {
                writer.write("History:" + "\n");
                for (String str : story) {
                    writer.write(str + "\n");
                }
                writer.write("/..." + "\n");
                writer.flush();
            }
            catch (IOException ignored) {}
        }
    }
}
