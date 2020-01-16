# testng-example

- [Github, Testng](https://github.com/cbeust/testng)
- [testng.org](https://testng.org/doc/)

- [易百教程 - Testng](https://www.yiibai.com/testng/)



## Annotation

### `org.testng.annotations.@Test`

### `org.testng.annotations.@DataProvider`

### `org.testng.annotations.@Parameters`

### `org.testng.annotations.@Optional`

- [在TestNG中传递参数给方法使用](https://blog.csdn.net/libertine1993/article/details/80697470)

```
@Test
@Parameters("browser")
public void openBrowser(@Optional("chrome")String browser){
    // ...
}
```
一般与`@Parameters`配合使用，如果在`testng.xml`文件中没有找到名为"browser"的参数，测试方法将接受在@Optional注解中指定的默认值："chrome"。