# HKUST COMP3111 Software Engineering (Spring 2018)

Team Name: "<NULL>"

Team Members:
- Chan, Wing Kin [wkchanaj@ust.hk](mailto:wkchanaj@ust.hk): Team Leader
- Chung, Yuen Ting [ytchung@ust.hk](mailto:ytchung@ust.hk): Team Member
- Frost, Michael [mffrost@ust.hk](mailto:mffrost@ust.hk): Team Member

| Name | Email | Features | Best Commit | Best Pull |
|---|---|---|---|---|
| Chan, Wing Kin | wkchanaj@ust.hk | 3,5 | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/9a6a9d9b5f90e94e41334008872865db04887368, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/c0bc771fb73f3847d398ca9160557bdbf04fd7c5, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/ba2432d2a45dcc588a2be5334f9a0a8b5c808210  | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/pull/16 |
| Chung, Yuen Ting | ytchung@ust.hk  | 2,6 | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/bc476e53575b466d6a8c53ef7efeb7f3b84656ce, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/ffbc15ef892845342d5c1735aca6c95ee1ebc580, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/e44a0f4cbf98d858ccbddf80d35e2a33f26ac800  | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/pull/19  |
| Frost, Michael   | mffrost@ust.hk  | 1,4  | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/384c12f584092bbd3af450765b24ce3c926e622d, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/68d7cc4d0ed118bf27cc04589164c7a7ea9e2b95, https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/commit/0752940f26083f2373922f19b02dcd8547c2d62a  | https://github.com/thefrostysnowman/COMP3111-S2018G19-NULL/pull/17 |

Reference:
  https://stackoverflow.com/questions/39126331/how-do-you-create-an-animated-linechart-in-javafx

Quick reference links:

- [Project Sign Up](https://docs.google.com/spreadsheets/d/1NKxQflvfnRKmjrX8E_HLnRCC6kr4spUST_9fcyZIgFo/edit#gid=0)
- [Project Description](https://course.cse.ust.hk/comp3111/Project/comp3111-project-s2018.pdf)
- [Project FAQs](faq.md)

This GitHub repo hosts the base code of COMP3111 group project. The following features are provided

- A Java codebase which can be imported to `Eclipse IDE`. 
- Sample data handling classes are implemented in `core.comp3111` package
- A sample JUnit test (DataColumnTest) is implemented in `testing.comp3111` package (with 100% test coverage on DataColumn)
- A sample JavaFx GUI component is implemented in `ui.comp3111` package

Instructions to clone this GitHub project:

- Follow the instructions to [install JavaFx](https://www.eclipse.org/efxclipse/install.html) to your `Eclipse IDE`
- Restart `Eclipse IDE`
- Select `File > Import...`
- Select `Git > Projects` 
- Select `Clone URI` and then click `Next >`
- Copy and paste the URL from the web browser to the text box of `URI`
- Click `Next >` buttons a few times. Accept all default settings.
- Click `Finish` at the end

After importing this GitHub repo, the Eclipse project explorer should be displayed as follows: 

![Eclipse Project Explorer](eclipse_project.png)

Steps to run the Java GUI application
- Right-click the project folder
- Select `Run As > Java Application`

Steps to run the unit test and generate the coverage report
- Right-click the project folder
- Select `Coverage As > JUnit Test`
- All unit tests in `testing.comp3111` should be executed 
- A coverage report should be generated as follows:

![A sample coverage report](sample_coverage.png)




