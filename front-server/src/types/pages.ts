export class Page<T> {
  constructor(public readonly content: T[],
              public readonly pageInfo: PageInfo) {
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

export const FIRST_PAGE_REQUEST: PageRequest = {
  page: 0,
  size: 20,
  sort: [],
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

  public toPageRequest(page: number = this.currentPage + 1): PageRequest {
    return {
      page: page - 1, // server-side pages start with 0.
      size: this.size,
      sort: this.sort,
    }
  }
}
