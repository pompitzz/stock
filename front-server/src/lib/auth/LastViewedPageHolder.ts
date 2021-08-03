const KEY = 'LAST_VIEWED_PAGE_PATH'

export default class LastViewedPageHolder {
  public static save(path: string) {
    localStorage.setItem(KEY, path)
  }

  public static getAndRemove() {
    const redirectPath = localStorage.getItem(KEY);
    localStorage.removeItem(KEY);
    return redirectPath;
  }
}
