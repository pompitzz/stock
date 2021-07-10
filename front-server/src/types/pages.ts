export class Page<T> {
  constructor(private readonly content: T[],
              private readonly pageInfo: PageInfo) {
  }

  public static empty<T>(): Page<T> {
    return new Page<T>([], PageInfo.emptyPageInfo());
  }
}

export class ServerPageResponse<T> {
  constructor(public readonly content: T[],
              public readonly lastPage: boolean,
              public readonly size: number,
              public readonly currentPage: number,
              public readonly totalPages: number,
              public readonly sort: string[]) {
  }

  public toPage(): Page<T> {
    const pageInfo = new PageInfo(this.lastPage, this.size, this.currentPage, this.totalPages, this.sort);
    return new Page(this.content, pageInfo)
  }
}

export interface PageRequest {
  page: number,
  size: number,
  sort: string[],
}

export default class PageInfo {
  constructor(public readonly lastPage: boolean,
              public readonly size: number,
              public readonly currentPage: number,
              public readonly totalPages: number,
              public readonly sort: string[]) {
  }

  static emptyPageInfo(size: number = 20, sort: string[] = []) {
    return new PageInfo(false, size, 0, 0, sort)
  }

  public toPageRequest(): PageRequest {
    return {
      page: this.getCurrentPageForServer() + 1,
      size: this.size,
      sort: this.sort,
    }
  }

  // Api Server treats the first page as 0, but Front Server as 1.
  private getCurrentPageForServer() {
    return this.currentPage - 1;
  }
}
