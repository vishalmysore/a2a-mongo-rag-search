package vishalmysore;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts structured Java object string representations (e.g., TaskSendParams(...) or JSON-style strings)
 * into natural language summaries suitable for semantic search and indexing.
 * <p>
 * This conversion is performed as part of a Retrieval-Augmented Generation (RAG) pipeline where:
 * <ul>
 *     <li>Objects representing conversations, tasks, messages, etc. need to be stored as searchable text.</li>
 *     <li>Vector databases require semantically meaningful text (rather than nested or structured JSON) for embedding generation.</li>
 *     <li>This method extracts top-level fields and creates a human-readable summary that captures the context of the object.</li>
 * </ul>
 *
 * Example:
 * <pre>
 * Input: TaskSendParams(id=..., sessionId=..., message=Message(...))
 * Output: "This is an object of type TaskSendParams with fields: id=..., sessionId=..., message=..."
 * </pre>
 *
 * This natural language form improves semantic matching during search and question-answering in the RAG system.
 */

@Log
@Service
public class ObjectStringSummarizer {

    public  String summarize(String input) {
        String objectName = extractObjectName(input);
        Map<String, String> fields = extractTopLevelFields(input);

        StringBuilder summary = new StringBuilder();
        summary.append("This is an object of type ").append(objectName).append(" with the following fields:\n");

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value.length() > 150) {
                value = value.substring(0, 150).trim() + "...";
            }

            summary.append(" The field ").append(key).append(" has value ").append(value).append("\n");
        }

        return summary.toString();
    }

    private  String extractObjectName(String input) {
        int parenIndex = input.indexOf('(');
        if (parenIndex == -1) return "Unknown";
        return input.substring(0, parenIndex).trim();
    }

    private  Map<String, String> extractTopLevelFields(String input) {
        Map<String, String> map = new LinkedHashMap<>();

        int start = input.indexOf('(');
        int end = input.lastIndexOf(')');
        if (start == -1 || end == -1 || end <= start) return map;

        String body = input.substring(start + 1, end);
        List<String> parts = splitByTopLevelCommas(body);

        for (String part : parts) {
            int equalsIdx = part.indexOf('=');
            if (equalsIdx > 0) {
                String key = part.substring(0, equalsIdx).trim();
                String value = part.substring(equalsIdx + 1).trim();
                map.put(key, value);
            }
        }

        return map;
    }

    private  List<String> splitByTopLevelCommas(String input) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        StringBuilder current = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') depth++;
            if (c == ')' || c == ']' || c == '}') depth--;
            if (c == ',' && depth == 0) {
                parts.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (!current.isEmpty()) parts.add(current.toString().trim());
        return parts;
    }

    public static void main(String[] args) {
        ObjectStringSummarizer summarizer = new ObjectStringSummarizer();
        String raw = "TaskSendParams(id=5b182934-00f9-4e72-ae93-e05626f4f1b5, sessionId=d49ac7ff-8524-4cc4-bc09-fa7d14172c22, message=Message(id=null, role=user, parts=[TextPart(id=null, type=text, text=getQuoteForLaundry for Indian wedding Kurta and Pyjama, metadata=null)], metadata={conversation_id=d49ac7ff-8524-4cc4-bc09-fa7d14172c22, conversation_name=, message_id=0bb12c3b-3849-4664-b96b-70dbba589b6a, last_message_id=a7da4686-b4fb-416b-b60f-f5849ab760be}), historyLength=0, pushNotification=null, metadata={conversation_id=d49ac7ff-8524-4cc4-bc09-fa7d14172c22}, acceptedOutputModes=[text, text/plain, image/png])";

        System.out.println(summarizer.summarize(raw));
    }
}
