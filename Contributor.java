import java.util.ArrayList;

public class Contributor {
    public String name;
    public int nSkills;
    public ArrayList<Skill> skills = new ArrayList<Skill>();
    boolean[] busy;

    public Contributor(String name, int nSkills) {
        this.name = name;
        this.nSkills = nSkills;
    }

    public void addSkill(String skill, int level) {
        skills.add(new Skill(skill, level));
        busy = new boolean[100000];
    }

    public String toString() {
        String r = name + " " + nSkills + " ";
        for (int i = 0; i < skills.size(); i++) {
            r += skills.get(i).name + " " + skills.get(i).level;
        }
        return r;
    }

    public boolean isAvailable(int first, int last) {
        for (int i = first; i < last+1; i++) {
            if (busy[i])
                return false;
        }
        return true;
    }

    public void assign(int first, int last) {
        for (int i = first; i < last+1; i++) {
            busy[i] = true;
        }
    }

    public int fill(int days) {
        int start, end, count;
        start = 0;
        count = 0;
        for(int i = 0; i < 500 && count != days; i ++) {
            if (count == 0 && (!busy[i])) {
                count += 1;
                start = i;
            }
            else if (count > 0 && !(busy[1])) {
                count += 1;
            }

            else if (count > 0 && busy[1]) {
                count = 0;
            }
        }
        if (count != days)
            return -1;
        return start;
    }

    public void unassign(int first, int last) {
        for (int i = first; i < last+1; i++) {
            busy[i] = false;
        } 
    }
}
