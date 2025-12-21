# 🎮 Puzzle Game v1

## 📋 Description

**Puzzle Game** est un jeu Tetris classique implémenté en Java avec JavaFX. Le projet met en avant l'utilisation de **Design Patterns** avancés pour démontrer les bonnes pratiques de génie logiciel.

### Fonctionnalités Principales
- ✅ Jeu Tetris complet avec grille 10x20
- ✅ Système d'état avancé avec State Pattern
- ✅ Décorateurs pour les power-ups (SpeedBoost, Shield)
- ✅ Système de niveaux dynamiques
- ✅ Logging complet et traçable
- ✅ Interface graphique moderne avec JavaFX
- ✅ Système de score et de progression

---

## 👥 Membres du Groupe


**Mohamed Bouafif** 
**Mariem Charef** 

---

## 🛠️ Technologies Utilisées

| Technologie | Version | Usage |
|------------|---------|-------|
| **Langage** | Java 21 | Langage principal |
| **Framework GUI** | JavaFX 21 | Interface graphique |
| **Logging** | Log4j2 2.20.0 | Système de logging |
| **Build Tool** | Maven 3.9+ | Compilation et packaging |

### Architecture
```
├── Java 21+     (Langage principal)
├── JavaFX 21    (Interface graphique moderne)
├── Log4j2       (Logging avancé)
├── Maven        (Gestion des dépendances)
└── Git          (Contrôle de version)
```

---

## 🏗️ Design Patterns Implémentés

### 1. **State Pattern** 
**Localisation** : `src/com/puzzle/state_pattern/`

Gère les états des pièces du Tetris :
- `PieceState` - Interface abstraite
- `FallingState` - État de chute
- `LandedState` - État d'atterrissage

```java
public abstract class PieceState {
    public abstract boolean canMove();
    public abstract boolean canRotate();
}
```

**Avantage** : Permet de changer le comportement d'une pièce dynamiquement

### 2. **Decorator Pattern** 
**Localisation** : `src/com/puzzle/decorator_pattern/`

Système de power-ups sans modifier le code existant :
- `SpeedBoostDecorator` - Accélère la chute des pièces
- Structure extensible pour ajouter d'autres décorateurs

```java
public class SpeedBoostDecorator implements Command {
    private final Command wrappedCommand;
}
```

**Avantage** : Ajoute des comportements dynamiquement sans héritage

### 3. **Composite Pattern** 
**Localisation** : `src/com/puzzle/composite_pattern/`

Structure hiérarchique des pièces du Tetris :
- `PieceComponent` - Interface composite
- `Piece` - Composant concret
- Support de la composition d'objets

```java
public interface PieceComponent {
    void render(GraphicsContext gc, int cellSize);
    List<Position> getAbsoluteBlocks();

}
```

**Avantage** : Traite les objets individuels et composés de façon uniforme

### 4. **Factory Pattern** 
**Localisation** : `src/com/puzzle/factory_pattern/`

Création des pièces Tetris sans connaître leurs détails :
- `TetrominoFactory` - Crée les 7 types de tétriminos
- Types supportés : I, O, T, S, Z, J, L

```java
public class TetrominoFactory {
    public static Piece createPiece() {
        
    }
}
```

**Avantage** : Centralise la logique de création, facilite la maintenance

### 5. **Command Pattern** 
**Localisation** : `src/com/puzzle/command_pattern/`

Encapsule les actions du jeu :
- `Command` - Interface pour les commandes
- `MoveCommand` - Déplace une pièce
- `RotateCommand` - Tourne une pièce

```java
public interface Command {
    boolean canExecute();
    void execute();
}
```

**Avantage** : Permet d'annuler/refaire les actions facilement

### 6. **Singleton Pattern**
**Localisation** : `src/com/puzzle/GameLogger.java`

Assure une seule instance du logger :

```java
public class GameLogger {
    private static GameLogger instance;
    
    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }
}
```

**Avantage** : Garantit une instance unique et globale

---

## 📦 Installation

### Prérequis

- **JDK 21** ou supérieur
  ```bash
  # Vérifier la version Java
  java -version
  ```

- **Maven 3.9+**
  ```bash
  # Vérifier la version Maven
  mvn -version
  ```

- **Git** (pour cloner le dépôt)
  ```bash
  # Vérifier Git
  git --version
  ```

### Étapes d'Installation

#### 1. Cloner le dépôt
```bash
git clone https://github.com/mariemcharef/PuzzleGameV1.git
cd PuzzleGameV1
```

#### 2. Compiler le projet
```bash
# Compilation complète avec Maven
mvn clean install

# Ou seulement la compilation
mvn clean compile
```

#### 3. Exécuter le jeu
```bash
# Option 1 : Exécuter directement
mvn javafx:run

# Option 2 : Créer un JAR et exécuter
mvn clean package
java -jar target/PuzzleGame-1.0-SNAPSHOT.jar

# Option 3 : Depuis l'IDE (IntelliJ, Eclipse)
- Ouvrir le projet
- Clic droit sur Puzzle.java
- Exécuter
```

### Dépendances Maven

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
    
    <!-- Log4j2 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```

---

## 🎮 Utilisation & Contrôles

### Contrôles du Clavier

| Touche | Action | Description |
|--------|--------|-------------|
| **← →** | Déplacer | Bouge la pièce à gauche/droite |
| **↑** | Tourner | Tourne la pièce de 90° |
| **↓** | Accélérer | Baisse la pièce plus vite |

### Objectifs du Jeu
- Remplir les lignes horizontales pour les éliminer
- Chaque ligne éliminée = 100 points
- 4 lignes d'un coup = Bonus niveau
- Atteindre le score le plus élevé possible

### Mécaniques

**Lignes Complètes**
```
████████████  ← Ligne complète
████████████  ← Ligne complète
████████████  ← Ligne complète
█ ███████████

Après élimination → Compression vers le bas
```

**Collisions**
```
Détection automatique :
- Collision avec le bas (game over si en haut)
- Collision avec les côtés (bloque le mouvement)
- Collision avec autres pièces (bloque la rotation)
```

---


## 🎯 Classes Principales

### Puzzle.java
**Classe principale - Contrôle du jeu**

```java
public class Puzzle extends Application {
    private BlockType[][] grid;           // Grille 10x20
    private Piece currentPiece;           // Pièce en cours
    private int score;                    // Score actuel
    private boolean gameOver;             // État du jeu
    private boolean isPaused;             // État pause
    private String gameState;             // État (MENU, PLAYING, etc.)
    
    @Override
    public void start(Stage primaryStage) { ... }
}
```

**Responsabilités**:
- Initialisation du jeu
- Gestion de la grille
- Boucle de jeu (game loop)
- Rendu graphique
- Gestion des entrées clavier

### Piece.java
**Représente une pièce Tetris**

```java
public class Piece implements PieceComponent {
    private BlockType type;               // Type (I, O, T, etc.)
    private List<Position> blocks;        // Blocs de la pièce
    private Color color;                  // Couleur affichage
    private Position position;            // Position sur la grille
    private PieceState state;             // État (Falling, Landed)
}
```

### GameLogger.java
**Singleton pour le logging complet**

```java
public class GameLogger {
    private static GameLogger instance;   // Instance unique
    private PrintWriter writer;           // Écriture en fichier
    
    public void logStateChange(...) { ... }
    public void logDecoratorApplied(...) { ... }
    public void logGameEvent(...) { ... }
}
```

**Logs enregistrés**:
- Changements d'état [STATE]
- Applications de décorateurs [DECORATOR]
- Événements du jeu [EVENT]
- Informations [INFO]

---

## 📊 Format des Logs

### Structure Générale
```
[YYYY-MM-DD HH:MM:SS] [LEVEL] Message
```

### Exemple Complet
```
[2024-12-20 10:15:23] [INFO] Game started
[2024-12-20 10:15:23] [STATE] Game: MENU -> PLAYING
[2024-12-20 10:16:02] [DECORATOR] SpeedBoost applied 
[2024-12-20 10:16:07] [DECORATOR] SpeedBoost removed 
[2024-12-20 10:17:45] [STATE] Game: PLAYING -> GAME_OVER
[2024-12-20 10:17:45] [INFO] Game Over - Final score: 250
```

### Niveaux de Log
- **[INFO]** - Informations générales
- **[STATE]** - Changements d'état
- **[DECORATOR]** - Power-ups
- **[EVENT]** - Événements du jeu
- **[ERROR]** - Erreurs


### Vérifier le Format des Logs
```bash
# Voir tous les logs
cat tetris_game.log

# Voir les changements d'état
grep "[STATE]" tetris_game.log

# Voir les décorateurs
grep "[DECORATOR]" tetris_game.log

# Voir les événements
grep "[EVENT]" tetris_game.log

# Suivre les logs en temps réel
tail -f tetris_game.log
```

---

## 🔍 Guide d'Intégration du Logging

Le système de logging a été intégré dans les classes principales :

### Dans Puzzle.java
```java
// Au démarrage
logger = GameLogger.getInstance();
logger.logGameStart();
logger.logStateChange("Game", "MENU", "PLAYING");

// Lors de collisions
logger.logCollision("Piece", "Grid");

// À la fin
logger.logGameOver(score);
logger.logStateChange("Game", "PLAYING", "GAME_OVER");
```

### Dans Piece.java
```java
// À la création
GameLogger.getInstance().logEntityCreated("Piece", type.name());

// Changement d'état
logger.logStateChange("Piece", "FallingState", "LandedState");
```

### Dans SpeedBoostDecorator.java
```java
// Application
GameLogger.getInstance().logDecoratorApplied("SpeedBoost");

// Retrait
GameLogger.getInstance().logDecoratorRemoved("SpeedBoost");
```

---

## 🚀 Améliorations Futures

### Court Terme
- [ ] Implémentation complète du menu principal
- [ ] Système de high scores sauvegardé
- [ ] Amélioration des graphiques
- [ ] Sons et musique

### Moyen Terme
- [ ] Multiplicateurs de score additionnels
- [ ] Power-ups avancés (Shield, Slow, etc.)
- [ ] Niveaux de difficulté progressifs
- [ ] Animations fluides

### Long Terme
- [ ] Mode multijoueur réseau
- [ ] Leaderboard en ligne
- [ ] Mode campagne avec niveaux
- [ ] Customisation des thèmes

### Design Patterns à Ajouter
- [ ] **Observer Pattern** - Pour les changements de score
- [ ] **Strategy Pattern** - Pour les algorithmes de collision
- [ ] **Adapter Pattern** - Pour la compatibilité des contrôles

---

## 🐛 Dépannage

### Le jeu ne démarre pas
```bash
# Vérifier Java
java -version

# Vérifier les dépendances
mvn dependency:tree

# Recompiler
mvn clean install
```

### Erreurs de compilation JavaFX
```bash
# S'assurer que JavaFX est installé
mvn dependency:get -Dartifact=org.openjfx:javafx-controls:21.0.1

# Recompiler avec Maven
mvn clean javafx:run
```

### Logs non générés
```bash
# Vérifier les permissions du répertoire
ls -la tetris_game.log

# S'assurer que GameLogger est initialisé
logger = GameLogger.getInstance();
logger.logGameStart();
```

## 🎓 Ressources Recommandées

### Design Patterns
- [Refactoring.Guru - Design Patterns](https://refactoring.guru/design-patterns)
- [Java Design Patterns](https://java-design-patterns.com/)

### JavaFX
- [Official JavaFX Documentation](https://openjfx.io/)
- [JavaFX Tutorials](https://docs.oracle.com/javase/8/javase-runtimes.html)

### Logging
- [Log4j2 Documentation](https://logging.apache.org/log4j/2.x/)
- [SLF4J Documentation](https://www.slf4j.org/)

### Maven
- [Maven Official Documentation](https://maven.apache.org/)
- [Maven Quick Start](https://maven.apache.org/guides/getting-started/)

---

**Dernière mise à jour** : 20 décembre 2024  
**Version du projet** : 1.0  

---

> "Les Design Patterns ne sont pas des solutions magiques, ce sont des outils pour écrire du code maintenable et évolutif."

**Bon jeu ! 🎮**

[📄 UML DIAGRAM](./umldiagram.png)
