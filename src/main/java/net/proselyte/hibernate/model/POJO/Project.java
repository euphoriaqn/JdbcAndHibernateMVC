package net.proselyte.hibernate.model.POJO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProjectName")
    private String name;
    @Column(name = "ProjectCost")
    private BigDecimal cost;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "developers_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers = new HashSet<Developer>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "customers_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Project> customersProj = new HashSet<Project>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Company Comp = new Company();

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Project() {
    }
    public Project(String projectName, BigDecimal projectCost) {
        this.name = projectName;
        this.cost = projectCost;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Project> getCustomersProj() {
        return customersProj;
    }

    public void setCustomersProj(Set<Project> customersProj) {
        this.customersProj = customersProj;
    }

    public Company getComp() {
        return Comp;
    }

    public void setComp(Company comp) {
        this.Comp = comp;
    }

    public Project withId(Long id){
        this.id = id;
        return this;
    }
    public Project withName(String name){
        this.name = name;
        return this;
    }
    public Project withCost(BigDecimal cost){
        this.cost = cost;
        return this;
    }


}
