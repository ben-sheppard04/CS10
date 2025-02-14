import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class SpeechTagger {
    private Map<String, Map<String, Double>> observations;
    private Map<String, Map<String, Double>> transitions;

    public SpeechTagger() {
        observations = new HashMap<String, Map<String, Double>>();
        transitions = new HashMap<String, Map<String, Double>>();
    }
    public void train() throws IOException {
        BufferedReader in;
        BufferedReader in2;
        try {
            in = new BufferedReader(new FileReader("texts/brown-train-sentences.txt"));
            in2 = new BufferedReader(new FileReader("texts/brown-train-tags.txt"));
            String wordsLine;
            String tagsLine;
            while ((wordsLine = in.readLine()) != null) {
                wordsLine = wordsLine.toLowerCase();
                tagsLine = in2.readLine().toLowerCase();
                String[] trainObs = wordsLine.split(" ");
                String[] trainTags = tagsLine.split(" ");
                for (int i = 0; i < trainObs.length; i++) {
                    String currentObservation = trainObs[i];
                    String currentTag = trainTags[i];
                    if (!observations.containsKey(currentTag)) {
                        observations.put(currentTag, new HashMap<String,Double>());
                        observations.get(currentTag).put(currentObservation, 1.0);
                    }
                    else {
                        if (!observations.get(currentTag).containsKey(currentObservation)) {
                            observations.get(currentTag).put(currentObservation, 1.0);
                        }
                        else {
                            observations.get(currentTag).put(currentObservation, observations.get(currentTag).get(currentObservation) + 1.0);
                        }
                    }
                    if (i == 0) { // previous is #
                        if (!transitions.containsKey("#")) {
                            transitions.put("#", new HashMap<String, Double>());
                            transitions.get("#").put(currentTag, 1.0);
                        }
                        else {
                            if (!transitions.get("#").containsKey(currentTag)) {
                                transitions.get("#").put(currentTag, 1.0);
                            }
                            else {
                                transitions.get("#").put(currentTag, transitions.get("#").get(currentTag) + 1.0);
                            }
                        }
                    }
                    else {
                        String prevTag = trainTags[i-1];
                        if (!transitions.containsKey(prevTag)) {
                            transitions.put(prevTag, new HashMap<String, Double>());
                            transitions.get(prevTag).put(currentTag, 1.0);
                        }
                        else {
                            if (!transitions.get(prevTag).containsKey(currentTag)) {
                                transitions.get(prevTag).put(currentTag, 1.0);
                            }
                            else {
                                transitions.get(prevTag).put(currentTag, transitions.get(prevTag).get(currentTag) + 1.0);
                            }
                        }
                    }
                }
            }
            for (String det : observations.keySet()) {
                double total = 0.0;
                for (String obsv : observations.get(det).keySet()) {
                    total += observations.get(det).get(obsv);
                }
                for (String obsv : observations.get(det).keySet()) {
                    observations.get(det).put(obsv, Math.log(observations.get(det).get(obsv) / total));
                }
            }
            for (String det : transitions.keySet()) {
                double total = 0.0;
                for (String obsv : transitions.get(det).keySet()) {
                    total += transitions.get(det).get(obsv);
                }
                for (String obsv : transitions.get(det).keySet()) {
                    transitions.get(det).put(obsv, Math.log(transitions.get(det).get(obsv) / total));
                }
            }
        }
        catch (Exception e) {
            throw new IOException("Cannot read training file.\n" + e);
        }
    }

    public List<String> viterbi(String input) {
        List<String> result = new LinkedList<String>(); // Linked List because adding at head of list
        List<Map<String, String>> backTrack = new ArrayList<Map<String, String>>();

        Set<String> currStates = new HashSet<String>();
        currStates.add("#");

        Map<String, Double> currScores = new HashMap<String, Double>();
        currScores.put("#", 0.0);

        String[] inputArray= input.split(" ");
        for(int i = 0; i < inputArray.length; i++) {
            Set<String> nextStates = new HashSet<String>();
            Map<String, Double> nextScores = new HashMap<String, Double>();
            for(String currState : currStates) {
                if(transitions.get(currState) == null) continue;
                for (String nextState : transitions.get(currState).keySet()) { // loops over neighbors
                    nextStates.add(nextState);
                    double nextScore = currScores.get(currState) + transitions.get(currState).get(nextState);
                    if (observations.get(nextState).get(inputArray[i]) != null) {
                        nextScore += observations.get(nextState).get(inputArray[i]);
                    } else {
                        nextScore -= 10;
                    }
                    if (!nextScores.containsKey(nextState) || nextScore > nextScores.get(nextState)) {
                        nextScores.put(nextState, nextScore);
                        if (backTrack.size() == i) {
                            backTrack.add(new HashMap<String, String>()); // Add map at index i (end) of ArrayList whenever moving to next observation (i increments)
                        }
                        backTrack.get(i).put(nextState, currState);
                    }
                }
            }
            currStates = nextStates;
            currScores = nextScores;
        }
        double highestScore = -1 * Double.MAX_VALUE;
        String highestState = "";
        for(String state : currScores.keySet()){
            if(currScores.get(state) > highestScore){
                highestScore = currScores.get(state);
                highestState = state;
            }
        }
        result.add(0, highestState);
        for(int i = backTrack.size() - 1; i > 0; i--) {
            result.add(0, backTrack.get(i).get(highestState));
            highestState = backTrack.get(i).get(highestState);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        SpeechTagger partOfSpeech = new SpeechTagger();
        partOfSpeech.train();
        System.out.println(partOfSpeech.observations);
        System.out.println(partOfSpeech.transitions);
        System.out.println(partOfSpeech.viterbi("The dog is red"));
    }
}
