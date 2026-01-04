## Project Report

**Authors:** Usman Ali Bokhari & Abdur Rahim  
**Date:** January 2026  

---
## 1. Work Process

### Development Timeline

**Part 1 deadline**
- Planning and design stage:
	- Brainstormed political simulation concept 
	- Designed class structure and relationships
	- Divided responsibilities between team members
- Implementation:
	- Usman: Implemented Player, GameManager, Main classes, added EventSystem, CommandProcessor, Ally management
	- Abdur Rahim: Developed Character hierarchy, Location, Item systems, created Opponent mechanics, Event system, CharacterType enum

**Part 2 deadline**
- Testing and refinement:
	- Increased difficulty with failure mechanics
	- Added speech backfire system (20% chance)
	- Expanded from 5 to 8 locations
	- Added neutral NPC interactions
	- Implemented risk/reward for all actions
- Documentation and Finalization:
	- Complete JavaDoc documentation
	- Created user manual and testing

We used github as our version control tool, to ensure smooth development and avoid conflicts. We reviewed each others code routinely as well to make sure we are on the same page.

---

## 2. Teamwork & Collaboration

### Division of Responsibilities

**Classes made by Usman:**
- Player.java - Player character with all actions and stats
- Main.java - Entry point and character creation
- GameManager.java - Core game loop and world management
- EventSystem.java - Random event management
- CommandProcessor.java - User input handling

**Classes made by Abdur Rahim:**
- Ally.java - Allied character system
- Opponent.java - Opposing character mechanics
- Location.java - Game world locations
- Item.java - Collectible items
- Event.java - Random events
- CharacterType.java - Character type enum
- Character.java - Abstract base class
---

## 3. Design Choices

### Object-Oriented Architecture

```
Character (abstract) - Abdur Rahim
├── Player - Usman
├── Opponent - Abdur Rahim
└── Ally - Abdur Rahim

GameManager - Usman
├── EventSystem - Usman
├── CommandProcessor - Usman
└── Location network - Abdur Rahim

Supporting Classes:
├── Item - Abdur Rahim
├── Event - Abdur Rahim
├── CharacterType - Abdur Rahim
└── Main - Usman
```

We have error handling in place, where incorrect inputs are handled and a proper response is given to the player. Prevents the user from abusing any of the games mechanics.
### Game Balance Philosophy

**Resource Scarcity:**
- All actions cost money ($15-40)
- Alliance maintenance: $5 per ally per turn
- Limited inventory: 10 items maximum

**Risk vs Reward:**
- Bribes: 60% base success + modifiers, costs $25-40
- Sabotage: 50% base success, costs $30, high scandal risk
- Speeches: 20% backfire chance, costs $15
- Media: Safe option, costs $15

**Win Conditions (All Required):**
- Popularity ≥ 75%
- Alliances ≥ 2
- Survive 15 turns
- Money > $0
- Scandal risk < 100%

### World Design

**8 Interconnected Locations:**
- Town Square (hub)
- Parliament Building
- Media Centre
- Downtown Slums
- Political Mansion
- University Campus
- Industry Park
- Suburban District
Each location contains characters and items.
### Character Types

| Type              | Money | Popularity | Influence | Scandal Risk |
| ----------------- | ----- | ---------- | --------- | ------------ |
| Business Tycoon   | $150  | 35%        | 25        | 10%          |
| Career Politician | $100  | 45%        | 30        | 20%          |
| Outsider          | $80   | 40%        | 15        | 5%           |
| Mafia Leader      | $200  | 25%        | 35        | 40%          |

---

## 4. Special Code Elements

### 4.1 Dynamic Success Calculation (Player.java)

```java
double successChance = 0.6 + (influence / 200.0) - (target.getDefenseLevel() / 100.0);
```

Player stats dynamically affect action outcomes, making character progression meaningful.

### 4.2 Speech Backfire Mechanic (Player.java)

```java
if (Math.random() < 0.20) {
    double damage = 5 + (Math.random() * 15);
    String[] backfireReasons = { /* 6 contextual messages */ };
}
```

Adds risk to safe actions with contextual narrative feedback.
### 4.3 Location Navigation (Location.java)

```java
private Map<String, Location> connections;

public void setConnection(String direction, Location location) {
    connections.put(direction.toLowerCase(), location);
}
```

HashMap enables O(1) connection lookups and flexible world design.

### 4.4 Neutral NPC System (GameManager.java)

```java
switch (npcName) {
    case "Concerned Citizen": // Free +3 popularity
    case "Investigative Journalist": // $25 to damage opponent
    case "Professor": // Free +5-10 influence
    // ... unique behavior for each
}
```

Each neutral NPC has unique mechanics making exploration rewarding.

### 4.5 Use proper Copying (Player.java)

```java
public List<Ally> getAlliances() { 
    return new ArrayList<>(alliances); // prevents external modification
}
```

Demonstrates proper encapsulation principles.

---

## 5. User Manual

### Getting Started

**Installation:**
```bash
javac *.java
java Main
```

**Character Creation:**
1. Enter your name
2. Choose character type (1-4)

### Essential Commands

**Movement:** `move north/south/east/west`  
**Items:** `take/drop/use <item>`, `inventory`  
**Actions:** `speech`, `bribe <name>`, `ally <name>`, `sabotage <name>`, `media`, `talk <npc>`  
**Info:** `stats`, `location`, `opponents`, `allies`, `help`  
**Game:** `end` (end turn), `quit` (exit)

### Winning Strategy

**Early Game (Turns 1-5):**
- Explore all locations, collect items
- Talk to neutral NPCs (free bonuses)
- Build influence to 25+ (required for alliances)

**Mid Game (Turns 6-10):**
- Form 2+ alliances (win requirement)
- Use speeches, but carefully (20% backfire rate)
- Manage scandal risk with media

**Late Game (Turns 11-15):**
- Maintain 75%+ popularity
- Keep scandal risk below 80%
- Reserve $50+ for emergencies

**Critical Tips:**
- Never hit $0 money (instant loss)
- Scandal risk 100% = game over
- Alliances cost $5 maintenance per turn
- Items are valuable - explore thoroughly

### Item Types
- **Political:** +popularity
- **Evidence:** +popularity, damages opponents
- **Valuable:** +money
- **Document:** +popularity
- **Tool:** Reusable
- **Consumable:** Single-use boosts
---
## 6. Conclusion

The game is fun and challenging! Having planned the structure before hand made the implementation much quicker and efficient. We made sure to comment properly and also maintain all the required development good practices, to ensure a well structured, easily understandable and workable project. 
