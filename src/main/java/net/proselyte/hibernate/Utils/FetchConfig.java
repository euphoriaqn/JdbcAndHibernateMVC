package net.proselyte.hibernate.Utils;

public class FetchConfig {
        private boolean skills = false;
        private boolean projects = false;
        private boolean developers = false;

        public boolean isDevelopers() {
                return developers;
        }

        public void setDevelopers(boolean developers) {
                this.developers = developers;
        }

        public boolean isSkills() {
                return skills;
        }

        public void setSkills(boolean skills) {
                this.skills = skills;
        }

        public boolean isProjects() {
                return projects;
        }

        public void setProjects(boolean projects) {
                this.projects = projects;
        }
}
