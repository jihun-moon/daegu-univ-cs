# App Programming — Course Summary

안드로이드 4대 컴포넌트와 Activity 생명주기를 이해하고, RecyclerView·ViewBinding·입력 검증으로 실사용 품질을 높였습니다. Intent 기반 컴포넌트 통신과 비동기 처리, ViewModel 상태 보존까지 실습으로 체득했습니다.

## Screenshot
![Login UI](assets/login-ui-design.png)

## What I Learned
- Components and Lifecycle: Activity/Fragment 생명주기와 상태 보존(ViewModel, SavedState)
- UI Patterns: RecyclerView + Adapter + DiffUtil, ViewBinding
- Input Validation: TextInputLayout 에러 피드백, 접근성 고려
- Inter-Component: Intent·Bundle 데이터 전달, Nav 흐름
- Async Basics: 코루틴 또는 스레드·핸들러를 이용한 비동기 처리

## Example
```kotlin
// RecyclerView + DiffUtil + ViewBinding
data class Item(val id: Long, val title: String)

class ItemDiff : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(a: Item, b: Item) = a.id == b.id
    override fun areContentsTheSame(a: Item, b: Item) = a == b
}

class ItemsAdapter :
    ListAdapter<Item, ItemsAdapter.VH>(ItemDiff()) {

    class VH(private val b: ItemRowBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(it: Item) { b.title.text = it.title }
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemRowBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, i: Int) = h.bind(getItem(i))
}
```

## Troubleshooting
- 스크롤 시 깜빡임: DiffUtil 적용으로 불필요한 재바인딩 감소
- 회전 시 데이터 유실: ViewModel + SavedState로 상태 보존
- 입력 오류 누락: TextInputLayout 에러 메시지와 규칙 추가

## Checklist
- [ ] 입력 검증 루틴 적용
- [ ] 생명주기 이벤트 처리 점검
- [ ] RecyclerView 성능 옵션(LayoutManager, setHasFixedSize)
- [ ] 코드 스타일·린트 통과
- [ ] 비동기 처리 시 UI 스레드 규칙 준수

## Folder Structure
```
/assets/                 # README 이미지 (login-ui-design.png 등)
/src/                    # 샘플 코드/과제
README.md
```

## Links
- Android Guides: https://developer.android.com/guide
- RecyclerView: https://developer.android.com/guide/topics/ui/layout/recyclerview
- Notion 정리: 앱 프로그래밍 페이지 참조

## License
MIT 또는 강의 요구사항에 맞는 라이선스 표기
