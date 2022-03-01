import java.util.ArrayList;

public class Project implements Comparable<Project> {
    public String name;
    public int duration;
    public int score;
    public int deadline;
    public ArrayList<Skill> skills = new ArrayList<Skill>();
    public int nSkills;
    public float scoreIndex;

    Project(String name, int duration, int score, int deadline, int nSkills) {
        this.name = name;
        this.duration = duration;
        this.score = score;
        this.deadline = deadline;
        this.nSkills = nSkills;
        scoreIndex = (float)score/(float)(nSkills*duration);
    }

    public void addSkill(String skill, int level) {
        skills.add(new Skill(skill, level));
    }

    public int compareTo(Project p) {
        return (int) (((float)p.score/(float)(p.duration*p.nSkills))*1000000 - ((float)score/(float)(duration*nSkills))*1000000); 
    }
}
