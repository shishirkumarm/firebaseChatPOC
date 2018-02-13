import { IshaPage } from './app.po';

describe('isha App', function() {
  let page: IshaPage;

  beforeEach(() => {
    page = new IshaPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
