package org.bluemagic.config.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public final class TagUtils {

    // Private Constructor so that it can't be instantiated.
    private TagUtils() {
    }

    public static String reorder(String unorderedTags) {
        // Split the original tags, so we can order them.
        String[] unsortedTags = unorderedTags.split("&");

        // Order the single tags.
        List<String> tagsToSort = new ArrayList<String>();
        List<String> unsortedSingleTags = new ArrayList<String>();
        for (String part : unsortedTags) {
            // Look for the single tags.
            if (part.startsWith("tags=")) {
                int index = part.indexOf("=");
                String tagsString = part.substring(index + 1);
                String[] tags = tagsString.split(",");

                for (String tag : tags) {
                    unsortedSingleTags.add(tag);
                }

                TreeSet<String> singleTags = new TreeSet<String>(
                        unsortedSingleTags);
                StringBuilder sortedSingleTagsString = new StringBuilder(
                        "tags=");
                while (!singleTags.isEmpty()) {
                    sortedSingleTagsString.append(singleTags.pollFirst());

                    if (!singleTags.isEmpty()) {
                        sortedSingleTagsString.append(",");
                    }
                }

                tagsToSort.add(sortedSingleTagsString.toString());
                // Not a single tag, so add it to the tags to sort.
            } else {
                tagsToSort.add(part);
            }
        }

        // Order the tags.
        TreeSet<String> sortedTags = new TreeSet<String>(tagsToSort);
        StringBuilder normalizedTags = new StringBuilder();

        while (!sortedTags.isEmpty()) {
            normalizedTags.append(sortedTags.pollFirst());

            if (!sortedTags.isEmpty()) {
                normalizedTags.append("&");
            }
        }

        return normalizedTags.toString();
    }
}
