# 💬 Y Microblogging Platform

This repository contains the source code and report for the **Y Microblogging** project, developed as part of the **Project Management and Software Engineering** course during the Master 1 in Computer Science at **Université Claude Bernard Lyon 1**.

The goal of this project is to design, implement, and test a **modular microblogging application** that follows software engineering principles and integrates advanced features such as dynamic scoring, API communication, and design pattern architecture.

---

## 🧭 Overview

**Y Microblogging** is a social platform that allows users to publish, organize, and visualize short messages.  
The system includes both **text** and **image** messages, supports **real-time translation**, and features **continuous scrolling** for improved user experience.

At startup, create a new user or log in using one of the demo accounts:  
```
Username: test  | Password: test
Username: admin | Password: admin
```

---

## 🧱 Architecture

The application is structured according to the **MVC (Model–View–Controller)** pattern.

### 🧩 Model
- **Message** – Represents a post with content, author, and metadata.  
- **TextMessage** / **ImageMessage** – Subclasses of `Message` for text or image content.  
- **MessageData** – Stores metadata such as scores and bookmarks.  
- **MessageDecorator** – Adds dynamic behavior (e.g., `ImportantMessageDecorator` for highlighting messages).  
- **ScoreStrategy** – Strategy interface for scoring algorithms:  
  - `BookmarkScoring`, `RecentMessageBonusScoring`, `LengthBasedScoring`  
- **DisplayStrategy** – Filters and sorts messages:  
  - `ChronologicalStrategy`, `MostRelevantStrategy`, `RecentRelevantStrategy`  
- **ApertiumApiClient** – Translates message content via the **Apertium translation API**.  
- **RedditAuth / RedditPost** – Authenticates and imports posts from Reddit’s API.  
- **DataInitializer** – Loads users and messages from a JSON configuration file.

### 🧠 Controller
- **MainController** – Core logic that links the model and view:  
  - Publish messages (via `TextMessageFactory`, `ImageMessageFactory`)  
  - Bookmark / unbookmark  
  - Subscribe / unsubscribe to users  
  - Delete messages dynamically  
  - Continuous scrolling (load more messages)  
  - Message translation (via Apertium API)  

### 🪟 View
- **JfxView** – JavaFX graphical interface for message display and interaction.  
- **SignupWindow** – User registration screen.  
- **LoginWindow** – User authentication screen.

---

## 🧠 Design Patterns Used

### 🏭 Factory Method
Used to create `Message` objects dynamically:  
- Abstract class `MessageFactory`  
- Concrete factories: `TextMessageFactory`, `ImageMessageFactory`  
➡️ Promotes **extensibility** and **Open/Closed Principle**.

### 🎨 Decorator
Used to dynamically add behaviors to messages:  
- `MessageDecorator` and `ImportantMessageDecorator`  
➡️ Enables **runtime flexibility** without modifying base classes.

### ⚙️ Strategy
Encapsulates interchangeable algorithms:  
- Scoring strategies (Bookmark, Length, Recency)  
- Display strategies (Chronological, Relevant, RecentRelevant)  
➡️ Encourages **behavioral modularity** and **easy experimentation**.

### 🧰 Façade
Simplifies API access (Apertium & Reddit):  
- `ApertiumApiClient`, `RedditAuth`, `RedditPosts`  
➡️ Provides **clean interfaces** for complex external services.  

### 🧩 GRASP Principle – Pure Fabrication
The API classes (`ApertiumApiClient`, `RedditPosts`, `RedditAuth`) centralize external logic for **high cohesion** and **low coupling**.

---

## 🌐 External API Integration

### 🌍 Apertium API
- Handles multilingual translation.  
- Uses HTTP requests and JSON parsing (via `HttpClient`, `Gson`).  
- Limitation: English → French requires an intermediate Spanish translation.

### 🔗 Reddit API
- Authenticates via OAuth2 using `RedditAuth`.  
- Fetches subreddit posts using `RedditPosts.getPosts()`.  
- Transforms Reddit posts into in-app messages.

---

## 🧪 Testing

### Automated (JUnit)
Covers all layers:
- **Models:** `Y`, `Message`, `User`, `MessageUserData`  
- **Controllers:** `MainController`, `App`  
- **Strategies:** `BookmarkScoring`, `ChronologicalStrategy`, `MostRelevantStrategy`, etc.  
- **Views:** `JfxView` interactions  
- **External APIs:**  
  - `ApertiumApiClient` – Translation and error handling  
  - `RedditAuth` / `RedditPosts` – Authentication and data retrieval

### Manual
- User interaction (publish, delete, translate).  
- Scrolling, filtering, and scoring validation.

---

## ⚖️ Ethical and Societal Considerations

The project explores the ethical challenges of **social media algorithms**, such as:  
- Filter bubbles (Eli Pariser, *The Filter Bubble*)  
- Amplification of extreme content (TikTok, YouTube cases)  
- Attention economy and user retention mechanisms  

The implementation demonstrates how **recommendation logic** can affect visibility and engagement.  
Future improvements include moderation systems, transparency tools, and privacy controls.

---

## 🧰 Technologies

| Category | Tools / Frameworks |
|-----------|--------------------|
| Language | Java 17+ |
| GUI | JavaFX |
| Testing | JUnit |
| APIs | Apertium, Reddit |
| JSON | Gson, JsonParser |
| HTTP | OkHttpClient, HttpClient |
| Build Tool | Maven or Gradle |

---

## 🚀 Running the Application

### Prerequisites
- Java 17+  
- Maven or Gradle  
- Internet connection (for API calls)

### Run Instructions
```bash
mvn clean compile javafx:run
```
or
```bash
gradle run
```

At startup, create an account or use existing demo credentials:
```
Username: test  | Password: test
Username: admin | Password: admin
```

---

## 🧩 Authors

**Alexandre COTTIER**  
Master’s student in Computer Science – *Image, Développement et Technologie 3D (ID3D)*  
Université Claude Bernard Lyon 1  

---

## 📜 License

This project is released for **academic and educational purposes** only.  
It is not intended for commercial deployment or distribution.

---

> *"Great architecture is not about adding complexity — it's about organizing simplicity."*
