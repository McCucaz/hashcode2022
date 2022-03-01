import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Core {

    public int nContributors;
    public int nProjects;
    HashMap<String,HashMap<Integer, ArrayList<Contributor>>> skills = new HashMap<String,HashMap<Integer, ArrayList<Contributor>>>();
    ArrayList<Project> projects = new ArrayList<Project>();

    public ArrayList<String> executeRandom(){
        ArrayList<String> output = new ArrayList<String>();
        return output;
    }

    public ArrayList<String> execute(){
        boolean assigned = false;
        int projectsDone = 0;
        projects.sort(null);
        ArrayList<String> output = new ArrayList<String>();
        output.add("");
        for (int i = 0; i < projects.size(); i++)
        {
            ArrayList<Contributor> projectContributors = new ArrayList<Contributor>();
            Project p = projects.get(i);
            if (p.deadline-p.duration < 0)
                continue;
            for (int s = 0; s < p.nSkills; s++) {
                assigned = false;
                Skill skill = p.skills.get(s);
                if (!(skills.containsKey(skill.name))) {
                    break;
                }
                for (int j = skill.level; j < 11; j++) {
                    if (!(skills.get(skill.name).containsKey(j)))
                        continue;
                    ArrayList<Contributor> possibleContributors = skills.get(skill.name).get(j);
                    if (possibleContributors.isEmpty())
                        continue;
                    Collections.shuffle(possibleContributors);
                    for (Contributor c : possibleContributors) {
                        if (c.isAvailable(p.deadline-p.duration, p.deadline-1)) {
                            projectContributors.add(c);
                            c.assign(p.deadline-p.duration, p.deadline-1);
                            assigned = true;
                            break;
                        }
                    }
                    if (assigned)
                        break;
                }
                if (!assigned) {
                    if (!(projectContributors.isEmpty())) {
                        for (Contributor c : projectContributors) {
                            c.unassign(p.deadline-p.duration, p.deadline-1);
                        }
                    }
                    break;
                }
            }
            if (projectContributors.size() == p.nSkills) {
                output.add(p.name);
                String names = "";
                for (Contributor c : projectContributors) {
                    names += c.name + " ";
                }
                output.add(names);
                projectsDone += 1;
                projects.remove(i);
                i-= 1;
            }
        }

        //PART 2 - FILL
        for (int i = 0; i < projects.size(); i++)
        {
            Project p = projects.get(i);
            for (int tries = 0; tries < 200; tries ++) {
                int start = -1;
                ArrayList<Contributor> projectContributors = new ArrayList<Contributor>();
                for (int s = 0; s < p.nSkills; s++) {
                    assigned = false;
                    Skill skill = p.skills.get(s);
                    if (!(skills.containsKey(skill.name))) {
                        break;
                    }
                    for (int j = skill.level; j < 11; j++) {
                        if (!(skills.get(skill.name).containsKey(j)))
                            continue;
                        ArrayList<Contributor> possibleContributors = skills.get(skill.name).get(j);
                        if (possibleContributors.isEmpty())
                            continue;
                        Collections.shuffle(possibleContributors);
                        for (Contributor c : possibleContributors) {
                            if (start == -1) {
                                start = c.fill(p.duration);
                                if (start == -1)
                                    continue;
                                else {
                                    projectContributors.add(c);
                                    c.assign(start, start+p.duration-1);
                                    assigned = true;
                                    break;
                                }
                            }
                            else {
                                if (c.isAvailable(start, start+p.duration-1)) {
                                    projectContributors.add(c);
                                    c.assign(start, start+p.duration-1);
                                    assigned = true;
                                    break;
                                }
                            }
                        }
                        if (assigned)
                            break;
                    }
                    if (!assigned) {
                        if (!(projectContributors.isEmpty())) {
                            for (Contributor c : projectContributors) {
                                c.unassign(start, start+p.duration-1);
                            }
                        }
                        break;
                    }
                }
                if (projectContributors.size() == p.nSkills) {
                    output.add(p.name);
                    String names = "";
                    for (Contributor c : projectContributors) {
                        names += c.name + " ";
                    }
                    output.add(names);
                    projectsDone += 1;
                    break;
                }
            }

        }
        output.set(0, "" + projectsDone);

        return output;
    }
}
