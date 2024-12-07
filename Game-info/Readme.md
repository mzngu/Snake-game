# Snake Game

## Overview
This is a classic Snake game implemented using LibGDX, featuring multiple levels, sound effects, and exciting gameplay mechanics.

## Features
- 3 Different Game Levels
    - Level 1: Wrap-around mode (snake passes through walls)
    - Level 2: Bordered mode (snake dies on wall collision)
    - Level 3: Obstacle mode (added obstacles make the game more challenging)

- Sound Effects
    - Background music
    - Eat sound
    - Game over sound
    - Music and sound effect toggles

- High Score Tracking
    - Automatically saves and displays highest score
    - Persistent across game sessions

- Responsive Controls
    - Arrow keys for snake movement
    - Pause game with 'P'
    - Return to menu with 'M'

## Controls
- **Arrow Keys**: Move Snake
- **P**: Pause Game
- **M**: Return to Main Menu
- **S**: Toggle Sound Effects
- **C**: Continue (in pause menu)
- **R**: Restart Game

## Game Modes
### Level 1 (Wrap-around)
- Snake passes through walls
- No border restrictions
- Easiest difficulty

### Level 2 (Bordered)
- Snake dies on wall collision
- Fixed game board
- Moderate difficulty

### Level 3 (Obstacle)
- Fixed game board
- Random obstacles added
- Highest difficulty

## Technical Details
- **Framework**: LibGDX
- **Language**: Java
- **Rendering**: ShapeRenderer
- **Audio**: LibGDX Sound and Music classes

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- LibGDX library

## Installation
1. Clone the repository
2. Ensure LibGDX is set up in your development environment
3. Import project dependencies
4. Run the `Main` class

## Project Structure
- `Main.java`: Application entry point
- `SnakeGame.java`: Core game logic
- `Snake.java`: Snake movement and collision logic
- `Food.java`: Food generation
- `Obstacle.java`: Obstacle generation for Level 3
- `SoundManager.java`: Audio management
- `HighScoreManager.java`: High score tracking

## Future Enhancements
- Difficulty levels
- Power-ups
- Multiplayer mode
- More complex obstacle generation

## Contribution
Contributions are welcome! Please fork the repository and submit a pull request.

## Authors
NIYUKURI Estimé & ES-SAFI Ayoub
