package com.example.school_administration_system.model;

public class Utilisateur {

        protected int id;
        protected String nom;
        protected String prenom;
        protected String email;
        protected String motDePasse;
        protected String telephone;

        public Utilisateur() {}

        public Utilisateur(String nom, String prenom, String email, String motDePasse, String telephone) {
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.motDePasse = motDePasse;
            this.telephone = telephone;
        }

        public boolean seConnecter(String email, String motDePasse) {
            return this.email.equals(email) && this.motDePasse.equals(motDePasse);
        }

        public void seDeconnecter() {
            System.out.println(prenom + " " + nom + " s'est déconnecté.");
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

        public String getTelephone() { return telephone; }
        public void setTelephone(String telephone) { this.telephone = telephone; }

        public String getNomComplet() {
            return prenom + " " + nom;
        }

}
