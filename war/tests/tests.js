var origFn = browser.driver.controlFlow().execute;

browser.driver.controlFlow().execute = function() {
  var args = arguments;

  // queue 100ms wait
  origFn.call(browser.driver.controlFlow(), function() {
    return protractor.promise.delayed(50);
  });

  return origFn.apply(browser.driver.controlFlow(), args);
};


describe('System test', function(){

  it('Should login', function(){
    element(by.linkText('Login')).click();
    element(by.model('usuario')).sendKeys('test');
    element(by.model('contrasena')).sendKeys('test');
    element(by.buttonText('Sign in')).click();
  });
  it('Should create a project', function(){
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/span[2]/button[1]')).click();
    element(by.model('nombreProyecto')).sendKeys('Proyecto de test');
    element(by.xpath('/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/textarea[1]')).click();
    element(by.xpath('/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/textarea[1]')).sendKeys("Descripcion de test");
    element(by.buttonText('Create')).click();
    
  });
  

  it('Should create a simulation', function(){
    element(by.buttonText('Create simulation')).click();
    element(by.model('name')).sendKeys('Simulacion de prueba');
    element(by.buttonText('Accept')).click();
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/article[1]/div[1]/div[1]/div[2]/button[1]')).click();
    element(by.model('selectedCircumscription.name')).clear();
    element(by.model('selectedCircumscription.name')).sendKeys('Madrid');
    element(by.model('selectedCircumscription.population')).clear();
    element(by.model('selectedCircumscription.population')).sendKeys('3000');
    element(by.model('selectedCircumscription.seats')).sendKeys('10');
    browser.pause();
    element(by.model('newVotingIntent.party.name')).click();
    element(by.model('newVotingIntent.party.name')).sendKeys('PP');
    element(by.model('newVotingIntent.voters')).sendKeys('1000');
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/article[1]/div[1]/div[2]/div[6]/table[1]/tbody[1]/tr[1]/td[3]/button[1]')).click();
    element(by.model('newVotingIntent.party.name')).sendKeys('PSOE');
    element(by.model('newVotingIntent.voters')).sendKeys('1000');
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/article[1]/div[1]/div[2]/div[6]/table[1]/tbody[1]/tr[2]/td[3]/button[1]/span[1]')).click();
    element(by.model('newVotingIntent.party.name')).sendKeys('PODEMOS');
    element(by.model('newVotingIntent.voters')).sendKeys('900');
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/article[1]/div[1]/div[2]/div[6]/table[1]/tbody[1]/tr[3]/td[3]/button[1]')).click();
    element(by.buttonText('Save simulation')).click();
    browser.wait(protractor.ExpectedConditions.alertIsPresent(), 2000); 
    browser.switchTo().alert().accept();
  });

  it("Should create a report",function(){
    element(by.buttonText('Save Report')).click();
    element(by.model('nameReport')).sendKeys('Reporte de prueba');
    element(by.buttonText('Create')).click();
  });

  it("Sould go back to project",function(){
    element(by.linkText('Proyecto de test')).click();
  });

  it('Publish and unpublish the report', function(){
    element(by.buttonText('Publish')).click();
    element(by.buttonText('Private')).click();
  });

  it('Should delete the simulation', function(){
    element(by.buttonText('Delete')).click();  });

  it('Should delete the report', function(){
    element(by.buttonText('Delete')).click();
  });

  it('Add a message', function(){
    element(by.buttonText('Add message')).click();
    element(by.model('message.title')).sendKeys('Mensaje de prueba');
    element(by.xpath('/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[2]/div[1]/div[2]/div[1]/textarea[1]')).click();
    element(by.xpath('/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[2]/div[1]/div[2]/div[1]/textarea[1]')).sendKeys("Cuerpo del mensaje de prueba");
    element(by.buttonText('Accept')).click();
  });

  it('Should delete the message', function(){
    element(by.buttonText('Delete')).click();
  });

  it('Should delete the project', function(){
    element(by.linkText('Projects')).click();
    element(by.css(".btn-danger")).click();
    element(by.linkText('Logout')).click();
  });
  
});

describe('Workgroup test', function(){

  it('Should login', function(){

    element(by.linkText('Login')).click();
    element(by.model('usuario')).sendKeys('test');
    element(by.model('contrasena')).sendKeys('test');
    element(by.buttonText('Sign in')).click();
  });
  it('Should create workgroup', function(){
    element(by.linkText('Work Groups')).click();
    element(by.xpath('/html[1]/body[1]/div[1]/div[2]/div[1]/article[1]/div[1]/div[1]/div[2]/button[1]')).click();
    element(by.xpath('/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]')).click();
    browser.actions().mouseUp().perform();
    element(by.model('workgroup.name')).click();
    element(by.model('workgroup.name')).sendKeys('Workgroup de test');
    element(by.buttonText('Create')).click();
  });
  it('Should add a member', function(){
    element(by.buttonText('Add member')).click();
    element(by.model('username')).sendKeys('test2');
    element(by.buttonText('Add')).click();
  });
  it('Should delete and logout', function(){
    element(by.css('[ng-click="removeUser(selectedWorkgroup,appUser)"]')).click();
    element(by.linkText('Logout')).click();
    
  });

});

describe('Profile test', function(){

  it('Should login', function(){

    element(by.linkText('Login')).click();
    element(by.model('usuario')).sendKeys('test');
    element(by.model('contrasena')).sendKeys('test');
    element(by.buttonText('Sign in')).click();
  });
  it('Should change email', function(){
   element(by.linkText('Profile')).click();
   element(by.buttonText('Change personal information')).click();
   element(by.buttonText('Change email')).click();
   element(by.model('newEmail')).sendKeys('test@example.com');
   element(by.buttonText('Change')).click();
   browser.wait(protractor.ExpectedConditions.alertIsPresent(), 2000); 
   browser.switchTo().alert().accept();

 });
  it('Should change role', function(){
    element(by.linkText('Profile')).click();
    element(by.buttonText('Change personal information')).click();
    element(by.buttonText('Change rol')).click();
    element(by.model('newRol')).sendKeys('Tester');
    element(by.buttonText('Change')).click();
    browser.wait(protractor.ExpectedConditions.alertIsPresent(), 2000); 
    browser.switchTo().alert().accept();
  });
  it('Should change password', function(){
    element(by.linkText('Profile')).click();
    element(by.buttonText('Change personal information')).click();
    element(by.buttonText('Change password')).click();
    element(by.model('oldPass')).sendKeys('test');
    element(by.model('newPass1')).sendKeys('test');
    element(by.model('newPass2')).sendKeys('test');
    element(by.buttonText('Change')).click();
    browser.wait(protractor.ExpectedConditions.alertIsPresent(), 2000); 
    browser.switchTo().alert().accept();
  });
  it('Should log out', function(){
    element(by.linkText('Logout')).click();
  });

});







