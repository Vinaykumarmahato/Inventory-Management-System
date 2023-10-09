📦🌟 **Inventory Management Project** 🌟📦

Welcome to the Inventory Management project repository! This guide will help you understand the essentials of this Java application and how to work with it efficiently.

## 🛠️ Build Output Description

When building your Java application, the IDE automates the process of copying all necessary JAR files from the project's classpath to the `dist/lib` folder. Additionally, it updates the `Class-Path` element in the application's JAR file manifest (MANIFEST.MF).

To run your project from the command line, navigate to the `dist` folder and use the following command:
shell
java -jar "InventoryManagement.jar"


## 📝 Notes
- 🔄 If multiple JAR files on the classpath share the same name, only the first encountered will be copied to the lib folder.
- 📦 Only JAR files are copied to the lib folder; other file types or folders in the classpath won't be included.
- 📚 If a library on the classpath specifies a Class-Path element in its manifest, ensure its content is on the project's runtime path.
-⚙️ To set the main class in your Java project, right-click the project node in the Projects window, choose Properties, navigate to Run, and enter the class name in the Main Class field. Alternatively, you can manually specify the class name in the manifest's Main-Class element.
-📌 Note
- Organize your project's dependencies and classpath meticulously for seamless development and distribution.

## 📋 Additional Resources

- For further troubleshooting, updates, and detailed documentation, please refer to the project's documentation and support channels.

- Explore this repository, and may your Java development journey be colorful and productive! 🌈🚀