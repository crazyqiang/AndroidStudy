
### 使用文档
| API  | 备注  |
|:--|:--|
| setModels(list: MutableList< String>)  | 设置轮播数据  |
| submitList(newList: MutableList< String>) | 使用DiffUtil进行增量数据更新 |
| setAutoPlay(isAutoPlay: Boolean) | 设置自动轮播 true-自动  false-手动 |
| setUserInputEnabled(inputEnable: Boolean) | 设置MVPager2是否可以滑动 true-可以滑动 false-禁止滑动 |
| setIndicatorShow(isIndicatorShow: Boolean) | 是否展示轮播指示器 true-展示 false-不展示 |
| setPageInterval(autoInterval: Long) | 设置自动轮播时间间隔 |
| setAnimDuration(animDuration: Int) | 设置轮播切换时的动画持续时间 通过反射改变系统自动切换的时间<br> **注意**：这里设置的animDuration值需要小于setPageInterval()中设置的autoInterval值 |
| setOffscreenPageLimit(@OffscreenPageLimit limit: Int) | 设置离屏缓存数量 默认是OFFSCREEN_PAGE_LIMIT_DEFAULT = -1 |
| setPagePadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) | 设置一屏多页 |
| setPageTransformer(transformer: CompositePageTransformer) | 设置ItemView切换动画， CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer |
| setOnBannerClickListener(listener: OnBannerClickListener) | 设置Banner的ItemView点击 |
| registerOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) | 设置页面改变时的回调 |
| setOrientation(@ViewPager2.Orientation orientation: Int) | 设置轮播方向，横竖方向：ORIENTATION_HORIZONTAL 或 ORIENTATION_VERTICAL |
| setLoader(loader: ILoader< View>) | 设置ItemView加载器 |
| isAutoPlay() | 是否自动轮播 |
