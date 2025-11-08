# TypeWriter Paper plugin

I just wanted to experiment with creating a flexible way to define custom typing animations.

I came up with this solution using the builder pattern: 

```java
final Writer writer = new Writer.Builder()
    .setTextColor(TextColor.color(0xFF0013))
    .setLoop(true)
    .type("Hello\n")
    .pause(5)
    .type("World!")
    .erase(6)
    .type("There")
    .delete()
    .type("nothing to lose")
    .delete()
    .build(plugin);
```
Demo:
https://youtu.be/l3YGxhewmJA
