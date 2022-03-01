
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class IO extends Thread {

    private BufferedReader _reader;
    private FileWriter _output;
    private Core _core;
    private String _name;

    public IO(String name) throws IOException {
        _name = name;
        _core = new Core();
        _reader = new BufferedReader(new FileReader(name));
        _output = new FileWriter(name.substring(0, name.length()-3)+"-output.txt");
    }

    public void readFile() throws IOException {
        String line;
        String name;
        int days;
        int score;
        int deadline;
        int nSkills;
        int level;
        line = _reader.readLine();
        String[] values = line.split(" ");
        _core.nContributors = Integer.parseInt(values[0]);
        _core.nProjects = Integer.parseInt(values[1]);

        for (int i = 0; i < _core.nContributors; i++) {
            line = _reader.readLine();
            values = line.split(" ");
            name = values[0];
            nSkills = Integer.parseInt(values[1]);
            Contributor c = new Contributor(name, nSkills);
            for (int j = 0; j < nSkills; j++) {
                line = _reader.readLine();
                values = line.split(" ");
                name = values[0];
                level = Integer.parseInt(values[1]);
                c.addSkill(name, level);
                if (!(_core.skills.containsKey(name))) {
                    HashMap<Integer, ArrayList<Contributor>> hash = new HashMap<Integer, ArrayList<Contributor>>();
                    _core.skills.put(name, hash);
                }
                if (!(_core.skills.get(name).containsKey(level))) {
                    ArrayList<Contributor> cs = new ArrayList<Contributor>();
                    _core.skills.get(name).put(level, cs);
                }
                _core.skills.get(name).get(level).add(c);
            }
        }

        for (int i = 0; i < _core.nProjects; i++) {
            line = _reader.readLine();
            values = line.split(" ");
            name = values[0];
            days = Integer.parseInt(values[1]);
            score = Integer.parseInt(values[2]);
            deadline = Integer.parseInt(values[3]);
            nSkills = Integer.parseInt(values[4]);
            Project p = new Project(name, days, score, deadline, nSkills);
            for (int j = 0; j < nSkills; j++) {
                line = _reader.readLine();
                values = line.split(" ");
                name = values[0];
                level = Integer.parseInt(values[1]);
                p.addSkill(name, level);
            }
            _core.projects.add(p);
        }
        _reader.close();
    }

    public void run() {
        super.run();
        try {
            readFile();
            writeResults();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deu merda");
        } 
    }

    public void writeResultsRandom() throws IOException {
        for (String s : _core.executeRandom()) {
            _output.write(s+"\n");
        }
        _output.flush();
        _output.close();
        System.out.println("O output do ficheiro "+ _name + " foi concluido com sucesso.");
    }
    
    public void writeResults() throws IOException {
        for (String s : _core.execute()) {
            _output.write(s+"\n");
        }
        _output.flush();
        _output.close();
        System.out.println("O output do ficheiro "+ _name + " foi concluido com sucesso.");
    }
}